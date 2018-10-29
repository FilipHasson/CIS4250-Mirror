<script>

import moment from 'moment'

export default {
  props: {
    recipeId: {
      type: Number,
      default: 0
    },
    showUser: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    model: function () {
      return this.$store.state.models.food[this.recipeId] || {}
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
  <tr class="recipe-line">
    <td class="recipe-line--star-container">
      <font-awesome-icon icon="star" class="recipe-line--star-icon" />
      <span class="recipe-line--star-count">{{stars}}</span>
    </td>
    <td>{{title}}</td>
    <td v-if="showUser" class="recipe-line--by-line">by @{{author}}</td>
  </tr>
</template>

<style lang="scss">

.recipe-line {

  &--star-container {
    width: 7ch;
    background-color: $colour-lighter;
  }

  &--star-icon {
    width: 20px;
    color: $colour-medium;
    stroke: transparent;
    padding-right: 1ch;
    stroke-width: 50;
    &:hover {
      color: yellow;
      stroke: hsl(50, 100%, 50%);
    }
  }

  &--star-count {
    font-weight: bold;
  }

  //   &--title {
  //     font-weight: bold;
  //     margin-right: 5px;
  //     margin-left: 5px;
  //   }

  //   &--by-line {
  //     margin-right: 5px;
  //   }
}
</style>
