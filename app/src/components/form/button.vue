<script>
export default {
  name: 'v-button',
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
  background-color: $colour-lighter;
  border: 2px solid transparent;
  border-radius: 5px;
  color: $colour-primary;
  font-weight: bold;
  padding: 0.25rem 0.75rem;
  transition-duration: 0.2s;
  &:hover {
    border-color: $colour-primary;
  }
}
</style>
