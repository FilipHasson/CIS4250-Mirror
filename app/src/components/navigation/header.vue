<script>
import LoginModal from '@/components/layout/modal/login'
import AccountModal from '@/components/layout/modal/account'
export default {
  components: {
    LoginModal,
    AccountModal
  },
  methods: {
    showModal: function () {
      this.$store.commit('showModal', this.relevantModal.name)
    }
  },
  computed: {
    isLoggedIn: function () {
      return this.$store.getters.isLoggedIn
    },
    routes: function () {
      return this.isLoggedIn
        ? this.$store.state.registry.routes.all
        : this.$store.state.registry.routes.public
    },
    relevantModal: function () {
      return this.isLoggedIn
        ? { name: 'Account', component: AccountModal }
        : { name: 'Login', component: LoginModal }
    }
  }
}
</script>

<template>
  <div class="navigation-header">

    <nav>
      <ul>
        <li v-for="route in routes" :key=route.path>
          <router-link :to="route.path">{{route.name}}</router-link>
        </li>
        <li><a @click="showModal">{{relevantModal.name}}</a></li>
      </ul>
    </nav>
    <component :is="relevantModal.component" />
  </div>
</template>

<style lang="scss">
nav {
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
      margin: 10px 10px 7px;
      text-align: center;
      // .router-link-exact-active {
      //   border-bottom-color: hsla(0, 0, 0, 0.1);
      // }
      a {
        color: $colour-lighter;
        cursor: pointer;
        font-family: "Roboto", sans-serif;
        text-decoration: none;
        border-bottom: 3px solid transparent;

        &.active {
          color: black;
        }

        &:hover {
          border-bottom-color: $colour-lighter;
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
