<template>
  <nav class="navigation-header">
    <ul>
      <li v-for="route in routes" :key=route.id>
        <router-link :to="route.path">{{route.name}}</router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import { routes } from '@/router'

export default {
  data: function () {
    return {
      routes: routes
    }
  }
}
</script>

<style lang="scss">
nav.navigation-header {
  background-color: $colour-primary;

  ul {
    display: flex;
    justify-content: flex-start;
    list-style: none;

    li {
      display: flex;
      flex-direction: column;
      font-size: 1rem;
      justify-content: center;
      margin: 10px;
      margin-top: 10px;
      margin-bottom: 7px;
      text-align: center;

      a {
        color: $colour-lighter;
        font-family: "Roboto", sans-serif;
        text-decoration: none;
        border-bottom: 3px solid transparent;

        &.active {
          color: black;
        }

        &:hover {
          border-bottom: 3px solid $colour-lighter;
        }
      }
      &:first-child {
        font-size: 1.5rem;
        font-weight: bolder;
      }
      &:last-child {
        margin-left: auto;
      }
    }
  }
}
</style>
