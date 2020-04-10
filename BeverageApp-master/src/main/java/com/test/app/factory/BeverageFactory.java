package com.test.app.factory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.test.app.bootstrap.DataModel;
import com.test.app.exception.InvalidOrderException;

public class BeverageFactory {

	Map<String, List<String>> beverages = DataModel.getBeveragesMap();

	Map<String, Double> itemRates = DataModel.getItemRates();

	Map<String, Double> ingredientRates = DataModel.getIngredientRates();

	public double getInvoiceFromOrder(String order) {
		double cost = 0.0d;
		List<String> orderItems = getItemsFromOrder(order.trim());
		for (String item : orderItems) {
			List<String> itemWithIngredients = getIngredientFromItem(item);
			checkIfValidOrder(item);
			cost = cost + calculateFinalInvoice(itemWithIngredients);
		}
		return cost;
	}

	private void checkIfValidOrder(String item) {
		List<String> itemIngredients = getIngredientFromItem(item);

		if (!beverages.containsKey(itemIngredients.get(0)))
			throw new InvalidOrderException(
					"Invalid item ordered -> " + item + " .Order again with valid menu item..!!");

		List<String> allIngredients = beverages.get(itemIngredients.get(0)); 
		Optional<String> duplicateIngredient = itemIngredients.stream()
				.filter(i -> Collections.frequency(itemIngredients, i) > 1).findFirst();
		if (duplicateIngredient.isPresent())
			throw new InvalidOrderException("Duplicate ingredient not allowed -> " + duplicateIngredient.get());

		List<String> ingredients = itemIngredients.subList(1, itemIngredients.size());
		boolean validIngredients = ingredients.stream().allMatch(t -> allIngredients.stream().anyMatch(t::contains));

		if (!validIngredients)
			throw new InvalidOrderException("Invalid ingredient in order.Please check and order again..!!");

		if (itemIngredients.size() == allIngredients.size() + 1)
			throw new InvalidOrderException("Invalid Order..!! You cannot exclude all items from " + item);

	}

	private Double calculateFinalInvoice(List<String> itemWithIngredients) {
		Double itemValue = itemRates.get(itemWithIngredients.get(0));
		Double ingredientsValue = 0.0d;
		if (itemWithIngredients.size() > 1)
			for (int i = 1; i < itemWithIngredients.size(); i++) {
				ingredientsValue = ingredientsValue + ingredientRates.get(itemWithIngredients.get(i));
			}
		return itemValue - ingredientsValue;
	}

	private List<String> getItemsFromOrder(String order1) {
		return Arrays.stream(order1.split("(?!,\\s*-),")).map(String::trim).map(String::toLowerCase)
				.collect(Collectors.toList());
	}

	private List<String> getIngredientFromItem(String item) {
		return Arrays.stream(item.split(",")).map(s -> s.replace("-", "")).map(String::trim)
				.collect(Collectors.toList());
	}

}
