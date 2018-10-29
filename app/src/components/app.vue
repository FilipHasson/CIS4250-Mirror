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
    <NavigationHeader />
    <router-view />
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
  font-size: 1.25rem;
  margin-bottom: 1rem;
  margin-top: 2rem;
}

h2 {
  font-size: 1.15rem;
  margin-bottom: 1rem;
}

.page {
  margin: 4.5rem 1rem;
}

.todo {
  color: $colour-primary;
  font-size: 2rem;
  font-weight: bold;
}
</style>
