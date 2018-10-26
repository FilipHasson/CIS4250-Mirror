import { publicRoutes, privateRoutes } from '@/scripts/helpers/routes'

export default {
  session: {
    user_id: localStorage.getObject('user_id'),
    shown_modal: null
  },
  registry: {
    routes: {
      public: publicRoutes,
      private: privateRoutes,
      all: publicRoutes.concat(privateRoutes)
    },
    modals: ['login', 'account'],
    category: ['Keto'],
    nutrition: {
      carbohydrate_fiber: 'Fiber',
      carbohydrate_sugar: 'Sugar',
      fat_monosaturated: 'Monounsaturated Fat',
      fat_polyunsaturated: 'Polyunsaturated Fat',
      fat_saturated: 'Saturated Fat',
      fat_trans: 'Trans Fat',
      protein: 'Protein',
      betaine: 'Betaine',
      folate: 'Folate',
      folic_acid: 'Folic Acid',
      niacin: 'Niacin',
      pantothenic_acid: 'Pantothenic Acid',
      riboflavin: 'Riboflavin',
      thiamine: 'Thiamine',
      vitamin_a: 'Vitamin A',
      vitamin_b12: 'Vitamin B12',
      vitamin_b6: 'Vitamin B6',
      vitamin_c: 'Vitamin C',
      vitamin_d: 'Vitamin D',
      vitamin_e: 'Vitamin E',
      vitamin_k: 'Vitamin K',
      calcium: 'Calcium',
      copper: 'Copper',
      fluoride: 'Fluoride',
      iron: 'Iron',
      magnesium: 'Magnesium',
      manganese: 'Manganese',
      phosphorus: 'Phosphorus',
      potassium: 'Potassium',
      selenium: 'Selenium',
      sodium: 'Sodium',
      zinc: 'Zinc',
      alcohol: 'Alcohol',
      caffeine: 'Caffeine',
      water: 'Water'
    }
  },
  models: {
    account: {},
    comment: {},
    food: {},
    meal: {}
  }
}
