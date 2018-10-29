<script>
import moment from 'moment'

export default {
  props: {
    recipeId: {
      type: Number,
      default: 0
    }
  },
  computed: {
    model: function () {
      return this.$store.state.models.food[this.recipeId]
    },
    author: function () {
      const accountModel = this.$store.state.models.account[this.model.account_id]
      return accountModel ? accountModel.username : ''
    },
    title: function () {
      return this.model.title || 'Untitled'
    },
    header: function () {
      return this.model.header || ''
    },
    portions: function () {
      return this.model.portions || 1
    },
    views: function () {
      return this.model.view_count || 0
    },
    stars: function () {
      return this.model.star_count || 0
    },
    created: function () {
      return moment.unix(this.model.time_created).fromNow()
    },
    modified: function () {
      return moment.unix(this.model.time_modified).fromNow()
    },
    categories: function () {
      return this.model.categories || []
    }
  },
  mounted () {
    this.$store.dispatch('fetchFood', this.recipeId)
  }
}
</script>

<template>
  <div class="card">

    <p class="card--by-line">Posted by @{{author}} {{created}}</p>
    <p class="card--title">{{title}}</p>

    <div class="card--star-container">
      <font-awesome-icon icon="star" class="card--star-icon" />
      <p class="card--star-count">{{stars}}</p>
    </div>

    <!-- <p class="card--tag">{{tag}}</p> -->
    <div class="card--image"></div>
    <!-- <p class="card--stats">{{stats}}</p> -->
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
  grid-template-columns: 1fr 2.5rem;

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
    height: 10rem;
    margin-top: 0.25rem;
    margin-bottom: 0.25rem;
  }

  &--stats {
    font-size: 0.75rem;
    font-weight: bold;
  }
}
</style>
