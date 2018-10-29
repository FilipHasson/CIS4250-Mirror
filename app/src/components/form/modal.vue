<script>
export default {
  props: {
    width: {
      type: String,
      default: '15rem'
    }
  },
  data: function () {
    return {
      showModal: false
    }
  },
  methods: {
    close: function () {
      this.$store.commit('closeModal')
    }
  },
  mounted () {
    // close modal if escape key is pressed
    document.body.addEventListener('keyup', e => {
      if (e.keyCode === 27) {
        this.close()
      }
    })
  }
}
</script>

<template>
  <div id="modal-template">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container" :style="{'width':width}">
          <div class="modal-header">
            <slot name="header" />
            <font-awesome-icon icon="times" class="close-icon" @click="close" />
          </div>
          <div class="modal-body">
            <slot name="body" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
.modal-header h1,h2,h3 {
  margin: 0 !important;
  padding: 0 !important;
  user-select: none;
}

.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  margin: 0 auto;
  background-color: #fff;
  border-radius: 0.15rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
}

.modal-body {
  padding: 1.1rem;
  min-height: 1.1rem;
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: $colour-primary;
  margin-top: 0;
  color: white;
  padding: 0.5rem;

  .close-icon {
    margin-left: auto;
    transition: 150ms;
    margin-right: 0.1rem;
    &:hover {
      color: hsla(0, 0, 0, 0.3);
      cursor: pointer;
    }
  }
}

input {
  width: 100%;
  padding:  0.5rem;
  margin-bottom: 0.5rem;
  display: inline-block;
  box-sizing: border-box;
  font-size: 0.8rem;
  border: 2px solid $colour-light;
  color: $colour-light;
  transition: 250ms;
  &:focus {
    border-color: black;
    color: black;
  }
}

button {
  color: white;
  border: none;
  background-color: $colour-light;
  padding: 8px 16px 8px 16px;
  margin-top: 8px;
  transition: 150ms;
  cursor: pointer;
  &:hover,
  &:focus {
    background-color: $colour-primary;
  }
}
</style>
