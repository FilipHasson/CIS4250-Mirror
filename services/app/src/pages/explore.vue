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
    <RecipeCard />
    <RecipeCard />
    <RecipeCard />
    <RecipeCard />
    <RecipeCard />
    <RecipeCard />
    <RecipeCard />
  </div>
</template>

<script>
import RecipeCard from '@/components/layout/recipe/card'

export default {
  name: 'CategoryPage',
  components: {
    RecipeCard
  }
}
</script>
