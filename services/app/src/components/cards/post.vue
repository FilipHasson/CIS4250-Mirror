<template>
  <div class="post-card">

    <p class="post-card--by-line">Posted by @{{user}} {{date}}</p>
    <p class="post-card--title">{{title}}</p>

    <div class="post-card--star-container">
      <font-awesome-icon icon="star" class="post-card--star-icon" />
      <p class="post-card--star-count">{{stars}}</p>
    </div>

    <!-- <p class="post-card--tag">{{tag}}</p> -->
    <div class="post-card--image"></div>
    <p class="post-card--stats">{{stats}}</p>
  </div>
</template>

<style lang="scss">
.post-card {
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

  &--stats{
    font-size: 0.75rem;
    font-weight: bold;
  }
}
</style>

<script>
export default {
  name: 'PostCard',
  data: function () {
    return {
      user: 'username',
      title: 'Morraccan Braised Chicken',
      tag: 'tag',
      date: '10 days ago',
      stars: '100k',
      stats: '4 Servings | 200 Calories | 12g Protein | 5g Carbs | 10g Fat'
    }
  }
}
</script>
