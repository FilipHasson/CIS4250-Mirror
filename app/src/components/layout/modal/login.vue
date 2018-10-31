<script>
import Modal from '@/components/form/modal'
import VButton from '@/components/form/button'
export default {
  components: {
    Modal,
    VButton
  },
  data: function () {
    return {
      username: '',
      password: ''
    }
  },
  computed: {
    shown: function () {
      return this.$store.state.session.shown_modal === 'login'
    }
  },
  methods: {
    login: function () {
      this.$store.dispatch('tryLogin', { username: this.username, password: this.password })
    }
  }
}
</script>

<template>
  <Modal v-if=shown>
    <h1 slot="header">Login</h1>
    <div slot="body">
      <form>
        <label for="login-modal--username-input">Username</label>
        <input type="text" autocomplete="username" v-model="username" id="login-modal--username-input">
        <label for="login-modal--password-input">Password</label>
        <input type="password" autocomplete="password" v-model="password" id="login-modal--password-input">
        <VButton :onClick="login">Submit</VButton>
      </form>
    </div>
  </Modal>
</template>
