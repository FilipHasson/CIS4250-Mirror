<script>
import Modal from '@/components/form/modal'
export default {
  components: {
    Modal
  },
  computed: {
    shown: function () {
      return this.$store.getters.currentModal === 'account'
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
      <button type="button" @click="logout">Logout</button>
    </div>
  </Modal>
</template>
