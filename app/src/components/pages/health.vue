<template>
  <div id="explore" class="page">
    <div @keydown.enter="handleSubmit">
      <label>
        Email:
        <input type="weight" v-model="user.weight"/>
      </label>
      <button @click="handleSubmit">Submit</button>
    </div>
    Expected Finish Date: {{ user.date }} <hr>
    Caloric Intake Goal: {{ user.goal }}
  </div>
</template>

<script>
import axios from 'axios'
export default {

  data () {
    return {
      user: {
        weight: '',
        goal: '',
        date: ''
      }
    }
  },
  methods: {
    handleSubmit () {
      var weight = parseInt(this.user.weight)
      var _this = this
      var url = '/api/account/info/weight/' + this.$store.state.session.user_id

      axios.post(url, {
        data: { weight }, meta: {}
      })
        .then(function (response) {
          var result = response.data
          _this.user.goal = result.weight
          _this.user.date = result.date
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>
