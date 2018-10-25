<script>
export default {
  data: function () {
    return {
      date: '10 days ago',
      stars: 100,
      stats: ['200 cals', '100g carbs'],
      tags: ['keto'],
      title: 'Moraccan Chicken',
      author: 'jessy'
    }
  },
  props: {
    recipeId: {
      type: Number,
      default: 0
    }
  }
}
</script>

<template>
  <div class="recipe-line">
    <div class="recipe-line--star-container">
      <font-awesome-icon icon="star" class="recipe-line--star-icon" />
      <p class="recipe-line--star-count">{{stars}}</p>
    </div>
    <p class="recipe-line--title">{{title}}</p>
    <p class="recipe-line--by-line">by @{{author}}</p>
    <p class="recipe-line--stats">{{stats}}</p>
  </div>
</template>

<style lang="scss">
.recipe-line {
  border-color: $colour-dark;
  border-style: solid;
  border-width: 1px;
  font-size: 0.75rem;

  display: flex;
  align-items: center;
  text-align: center;

  & ~ & {
    border-top-width: 0px;
  }

  &--star-container {
    background-color: $colour-lighter;
    padding: 3px;
  }

  &--star-icon {
    color: yellow;
    width: 20px;
    height: auto;
    stroke: $colour-dark;
    stroke-width: 25;
  }

  &--star-count {
    font-weight: bold;
    margin-right: 5px;
  }

  &--title {
    font-weight: bold;
    margin-right: 5px;
    margin-left: 5px;
  }

  &--by-line {
    margin-right: 5px;
  }
}
</style>
