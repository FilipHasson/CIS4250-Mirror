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
  methods: {
    createRecipe: () => alert('Created new recipe')
  }
}
</script>

<template>
  <div id="recipes" class="page">
    <h1>Your Recipes</h1>

    <div class="recipes--users-list">
      <RecipeLine v-for="i in 10" :key=i style="line" />
    </div>

    <!-- <VButton :onClick=getRecipe>Create New</VButton> -->

    <h1>Starred Recipes</h1>
    <div class="recipes--users-list">
      <RecipeLine v-for="i in 10" :key=i style="line" />
    </div>

  </div>
</template>

<style lang="scss">
.recipes--users-list {
  margin: 1rem 0 1rem 0;
}
</style>
