/**
 * fonts.js
 * imports and registers font glyphs for use in the application
 */

import Vue from 'vue'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'

// note: add fonts to library to use in the font-awesome-icon component
library.add(faStar)

Vue.component('font-awesome-icon', FontAwesomeIcon)
