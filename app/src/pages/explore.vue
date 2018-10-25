<style lang="scss">
#explore {
  display: grid;
  grid-template-columns: repeat(auto-fit, 500px);
  grid-template-rows: auto;
  grid-gap: 1rem;
  justify-content: center;
}
</style>

<template>
  <div id="explore" class="page">
    <RecipeCard
      v-for="id in latestFoodIds" :key="id"
      :recipeId=id
    />
  </div>
</template>

<script>
import RecipeCard from '@/components/layout/recipe/card'

export default {
  name: 'CategoryPage',
  components: {
    RecipeCard
  },
  mounted () {
    this.$store.dispatch('fetchFoods')
  },
  computed: {
    latestFoodIds: function () {
      return this.$store.getters.getLatestFoodIds()
    }
  }
}
</script>
