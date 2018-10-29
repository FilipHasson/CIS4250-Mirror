<script>
import Modal from '@/components/form/modal'
import VButton from '@/components/form/button'
export default {
  components: {
    Modal,
    VButton
  },
  computed: {
    shown: function () {
      return this.$store.state.session.shown_modal === 'account'
    },
    username: function () {
      return this.$store.getters.userName
    },
    email: function () {
      return this.$store.getters.userEmail
    }
  },
  methods: {
    logout: function () {
      this.$store.dispatch('tryLogout')
    }
  }
}
</script>

<template>
  <Modal v-if=shown>
    <h1 slot="header">Account</h1>
    <div slot="body">
      <p class="account-modal--field"><strong>username:</strong> {{username}}</p>
      <p class="account-modal--field"><strong>email:</strong> {{email}}</p>
      <VButton type="button" :onClick=logout>Logout</VButton>
    </div>
  </Modal>
</template>

<style lang="scss">
.account-modal--field{
  font-size: 0.75rem;
}
</style>
