<script>
import VButton from '@/components/form/button'

export default {
  components: {
    VButton
  },
  methods: {
    create: function (event) {
      this.$router.push('/recipes')
    },
    addStep: function (event, i) {
      this.steps.push('')
      this.$nextTick(function () {
        document.getElementById(`step-${this.steps.length - 1}`).focus()
      })
    },
    addIngredient: function (event, i) {
      this.ingredients.push('')
      this.$nextTick(function () {
        document.getElementById(`ingredient-${this.ingredients.length - 1}`).focus()
      })
    },
    exampleIngredient: function (i) {
      const examples = ['Mushrooms', 'Bacon', 'Tomatoes', 'Peaches']
      return examples[i % examples.length]
    },
    exampleStep: function (i) {
      const examples = ['e.g. Preheat Oven to 350°F', 'Stir until thickened', 'Heat to a boil']
      return examples[i % examples.length]
    }
  },
  data: function () {
    return {
      nutrition_fields: this.$store.state.registry.nutrition,
      steps: [''],
      ingredients: ['']
    }
  }
}
</script>

<template>
  <div id="recipe-new" class="page">
    <h1>New Recipe</h1>
    <div class="recipe-new--row">
      <div class="recipe-new--column">
        <input class="recipe-new-title title ghost" placeholder="Title">
        <textarea-autosize
          class="recipe-new-description ghost"
          placeholder="Description"
          width="100%"
        />
        <h2>Ingredients</h2>
        <div v-for="(ingredient, i) in ingredients" :key="'ingredient'+i" class="recipe-new--row">
          <p class="recipe-new--row--count">{{i+1}}.</p>
          <input
            @keyup.enter="addIngredient"
            :id="['ingredient-' + i]"
            class="recipe-new-ingredient ghost"
            :placeholder="exampleIngredient(i)"
            v-model="ingredients[i]"
          >
        </div>
        <h2>Steps</h2>
        <div v-for="(step, i) in steps" :key="'step-'+i" class="recipe-new--row">
          <p class="recipe-new--row--count">{{i+1}}.</p>
          <input
            @keyup.enter="addStep"
            :id="['step-' + i]"
            class="recipe-new-step ghost"
            :placeholder="exampleStep(i)"
            v-model="steps[i]"
          >
        </div>
      </div>
      <div class="recipe-new--column">
        <h2>Nutrition</h2>
        <table class="recipe-new--table">
          <tbody>
            <tr v-for="(name, field) in nutrition_fields" :key="field">
              <td class="recipe-new--table-field">{{name}}</td>
              <td class="recipe-new--table-value" contenteditable="true">0</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <VButton class="center" :onClick="create">Create</VButton>
  </div>
</template>

<style lang="scss">
  .recipe-new--row {
    display: flex;

  }
  .recipe-new--row {
    display: flex;
    &--count {
      width: 3ch;
      font-style: italic;
    }
  }
  .recipe-new--table {
    &-field {
      width: 20ch;
    }
    &-value {
      width: 10ch;
    }
    td {
      padding: 0.25rem;
      border-width: 1px;
    }
    tr {
      border: none;
    }
  }

  .recipe-new--table-value{
    text-align: right;
    // width: 5ch;
  }
</style>
