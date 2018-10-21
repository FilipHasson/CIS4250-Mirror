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
    style: {
      type: String,
      default: 'card',
      validator: value => {
        return (['card', ''])
      }
    },
    recipeId: {
      type: Number,
      default: 0
    }
  }
}
</script>

<template>
  <div class="card">

    <p class="card--by-line">Posted by @{{author}} {{date}}</p>
    <p class="card--title">{{title}}</p>

    <div class="card--star-container">
      <font-awesome-icon icon="star" class="card--star-icon" />
      <p class="card--star-count">{{stars}}</p>
    </div>

    <!-- <p class="card--tag">{{tag}}</p> -->
    <div class="card--image"></div>
    <p class="card--stats">{{stats}}</p>
  </div>
</template>

<style lang="scss">
.card {
  background-color: $colour-lighter;
  padding: 0.75rem;

  border-color: $colour-dark;
  border-style: solid;
  border-width: 0.15rem;

  display: grid;
  grid-template-columns: 1fr 50px;

  &--by-line {
    font-size: 0.75rem;
    grid-column-start: 1;
  }

  &--title {
    grid-column-start: 1;
    font-weight: bold;
  }

  &--star-container {
    grid-row-start: 1;
    grid-row-end: 3;
    grid-column-start: 2;
    text-align: center;
    font-size: 0.75rem;
    font-weight: bold;
    [data-icon="star"] {
      color: yellow;
      width: 25px;
      height: auto;
      stroke: $colour-dark;
      stroke-width: 25;
    }
  }

  &--image {
    grid-column-start: 1;
    grid-column-end: 3;
    border-style: solid;
    border-width: 0.15rem;
    background-color: $colour-dark;
    height: 200px;
    margin-top: 0.25rem;
    margin-bottom: 0.25rem;
  }

  &--stats {
    font-size: 0.75rem;
    font-weight: bold;
  }
}
</style>
