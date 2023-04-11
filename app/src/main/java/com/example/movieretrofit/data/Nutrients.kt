package com.example.movieretrofit.data

class Nutrients() : java.io.Serializable {
    var grams: Int = 1
    var calories: Float = 0f
    var protein: Float = 0f
    var fat: Float = 0f
    var carb: Float = 0f

    constructor(
        _grams: Int,
        _calories: Float,
        _protein: Float,
        _fat: Float,
        _carbs: Float
    ) : this() {
        grams = _grams
        calories = _calories
        protein = _protein
        fat = _fat
        carb = _carbs
    }

    fun getNutrients(content: String): Nutrients {
        val calories =
            Regex("""\b(\d+)\s*calories\b""").find(content)?.groups?.get(1)?.value?.toFloat()
        val protein =
            Regex("""\b(\d+)\s*g of protein\b""").find(content)?.groups?.get(1)?.value?.toFloat()
        val fat = Regex("""\b(\d+)\s*g of fat\b""").find(content)?.groups?.get(1)?.value?.toFloat()
        val carbs =
            (calories!! - (fat!! * 9.3 + protein!! * 4.1) / 4.1).toString().split('.')[0].toFloat()
        return Nutrients(grams, calories, protein, fat, carbs)
    }

    fun getCurrentDaySum(foodItems: List<Food>): Nutrients {
        var currentDaySum = Nutrients()
        foodItems.forEach {
            currentDaySum += it.nutrients
        }

        return currentDaySum
    }

    fun getNutrientsWithCf(nutrients: Nutrients, diet: Diet): Nutrients {
        val sumGrams = nutrients.protein + nutrients.fat + nutrients.carb
        return Nutrients(
            0,
            nutrients.calories,
            (nutrients.protein / sumGrams) / diet.proteinCf * nutrients.grams,
            (nutrients.fat / sumGrams) / diet.fatCf * nutrients.grams,
            (nutrients.carb / sumGrams) / diet.carbsCf * nutrients.grams,
        )
    }

    operator fun plus(newNutrients: Nutrients): Nutrients {
        calories += newNutrients.calories
        protein += newNutrients.protein
        fat += newNutrients.fat
        carb += newNutrients.carb
        return this
    }
}


