<script>
import NavigationHeader from '@/components/navigation/header'
export default {
  name: 'app',
  components: {
    NavigationHeader
  },
  mounted: function () {
    // load user data if available
    const userId = this.$store.state.session.user_id
    if (userId) {
      this.$store.dispatch('fetchAccount', userId)
    }
  }
}
</script>

<template>
  <div id="app">
    <NavigationHeader/>
    <router-view/>
  </div>
</template>

<style lang="scss">
  html {
    font-size: 18px;
  }

  body {
    -moz-osx-font-smoothing: grayscale;
    -webkit-font-smoothing: antialiased;
    font-family: sans-serif;
  }

  * {
    margin: 0;
    padding: 0;
    :focus {
      outline: none;
    }
  }

  h1 {
    font-size: 2rem;
    margin-bottom: 1rem;
    margin-top: 2rem;
  }

  h2 {
    font-size: 1.15rem;
    margin-bottom: 1rem;
  }

  table {
    border-collapse: collapse;
  }

  tr {
    border: 3px solid $colour-medium;
  }

  td {
    border: 3px solid $colour-medium;
    text-align: left;
    padding: 8px;
  }

  input,
  textarea {
    width: 100%;
    margin-bottom: 0.5rem;
    margin-bottom: 0.5rem;
    padding: 0.25rem;
    display: inline-block;
    box-sizing: border-box;
    font-size: 0.8rem;
    border: 2px solid $colour-light;
    color: $colour-light;
    transition: 250ms;
    font-size: 1rem;

    &:focus {
      border-color: black;
      color: black;
    }
    &.ghost {
      border: none;
      margin-bottom: 1rem;
      color: black;
    }
    &.title {
      font-size: 1.5rem;
    }
  }

  .page {
    margin: 4.5rem 1rem;
  }

  .todo {
    color: $colour-primary;
    font-size: 2rem;
    font-weight: bold;
  }

  .spacer {
    margin-top: 1rem;
  }
</style>
