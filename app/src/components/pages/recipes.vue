<script>
import VButton from '@/components/form/button'
import RecipeCard from '@/components/layout/recipe/card'
import RecipeLine from '@/components/layout/recipe/line'

export default {
  components: {
    VButton,
    RecipeCard,
    RecipeLine
  },
  computed: {
  },
  methods: {
    createRecipe: () => alert('Created new recipe')
  }
}
</script>

<template>
  <div id="recipes" class="page">
    <h1 class="recipes-title">Your Recipes</h1>
    <table class="recipes-user-table">
      <RecipeLine :recipeId=1 :showUser=false />
      <RecipeLine :recipeId=2 :showUser=false />
      <RecipeLine :recipeId=3 :showUser=false />
      <!-- <RecipeLine v-for="id in latestFoodIds" :key="id"
      recipeId=id
      /> -->
    </table>

    <VButton :onClick=createRecipe>Create New</VButton>

    <h1 class="recipes-title">Starred Recipes</h1>
    <div class="recipes-starred-list">
      <p class="recipes-empty">None 😭</p>
    </div>

  </div>
</template>

<style lang="scss">

.recipes-title {
  margin: 1.5rem 0 0.5rem 0;
}

.recipes--empty {
  color: $colour-light;
  font-weight: bold;
}

</style>
