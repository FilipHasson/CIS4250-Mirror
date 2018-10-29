<script>
export default {
  props: {
    onClick: {
      type: Function,
      required: true
    }
  }
}
</script>

<template>
  <button @click="onClick" class="v-button">
    <slot>Text</slot>
  </button>
</template>

<style lang="scss">
.v-button {
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
