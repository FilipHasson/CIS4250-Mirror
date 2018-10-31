import { routes } from '@/scripts/helpers/routes'

export default {
  session: {
    user_id: localStorage.getObject('user_id'),
    shown_modal: null
  },
  registry: {
    routes: {
      all: routes.filter(r => r.meta.is_public !== undefined) || [],
      public: routes.filter(r => r.meta.is_public === true) || [],
      private: routes.filter(r => r.meta.is_public === false) || [],
      header: routes.filter(r => r.meta.in_header === true) || []
    },
    modals: ['login', 'account'],
    category: ['Keto'],
    nutrition: {
      carbohydrate_fiber: 'Fiber (g)',
      carbohydrate_sugar: 'Sugar (g)',
      fat_monosaturated: 'Monounsaturated Fat (g)',
      fat_polyunsaturated: 'Polyunsaturated Fat (g)',
      fat_saturated: 'Saturated Fat (g)',
      fat_trans: 'Trans Fat (g)',
      protein: 'Protein (g)',
      folate: 'Folate (mcg)',
      niacin: 'Niacin (g)',
      riboflavin: 'Riboflavin (mg)',
      thiamine: 'Thiamine (mg)',
      vitamin_a: 'Vitamin A (iu)',
      vitamin_b6: 'Vitamin B6 (mg)',
      vitamin_c: 'Vitamin C (mg)',
      vitamin_d: 'Vitamin D (iu)',
      vitamin_k: 'Vitamin K (mcg)',
      calcium: 'Calcium (mg)',
      magnesium: 'Magnesium (mg)',
      manganese: 'Manganese (mg)',
      potassium: 'Potassium (mg)',
      sodium: 'Sodium (mg)',
      zinc: 'Zinc (mg)',
      alcohol: 'Alcohol (g)',
      caffeine: 'Caffeine (mg)',
      water: 'Water (ml)'
    }
  },
  models: {
    account: {},
    comment: {},
    food: {},
    meal: {}
  }
}
