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
  <Modal width="25rem" v-if="shown">
    <h1 slot="header">Account</h1>
    <div slot="body">
      <table>
        <tbody>
          <tr>
            <td class="account-table--key">username</td>
            <td class="account-table--value">{{username}}</td>
          </tr>
          <tr>
            <td class="account-table--key">email</td>
            <td class="account-table--value">{{email}}</td>
          </tr>
        </tbody>
      </table>
      <VButton type="button" :onClick="logout">Logout</VButton>
    </div>
  </Modal>
</template>

<style lang="scss">
  .account-table--key {
    font-weight: bold;
  }
</style>
