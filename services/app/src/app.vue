<template>
  <div id="app">
    <NavigationHeader />
    <router-view></router-view>
  </div>
</template>

<script>
import NavigationHeader from '@/components/navigation/header'
export default {
  name: 'app',
  components: {
    NavigationHeader
  }
}
</script>

<style lang="scss">
</style>
