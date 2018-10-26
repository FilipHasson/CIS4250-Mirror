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
      return this.$store.state.model_data.food[this.recipeId]
    },
    author: function () {
      const accountModel = this.$store.state.model_data.account[this.model.account_id]
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
  }
}
</script>

<template>
  <div class="recipe-line">
    <div class="recipe-line--star-container">
      <font-awesome-icon icon="star" class="recipe-line--star-icon"/>
      <p class="recipe-line--star-count">{{stars}}</p>
    </div>
    <p class="recipe-line--title">{{title}}</p>
    <p class="recipe-line--by-line">by @{{author}}</p>
    <!-- <p class="recipe-line--stats">{{stats}}</p> -->
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
