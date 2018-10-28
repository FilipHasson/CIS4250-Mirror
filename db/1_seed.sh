#!/bin/sh
set -e

TRANSFORMED_CSV_PATH=/var/lib/postgresql/seed.csv

COPY_COMMAND="
copy nutrition(
  usda_id,
  protein_g,
  total_lipid_g,
  carbohydrate_g,
  ash_g,
  water_g,
  sugars_g,
  total_fiber_g,
  solu_fiber_g,
  insolu_fiber_g,
  calcium_mg,
  iron_mg,
  magnesium_mg,
  phosphorus_mg,
  potassium_mg,
  sodium_mg,
  zinc_mg,
  manganese_mg,
  vitamin_a_iu,
  vitamin_d_iu,
  vitamin_c_mg,
  vitamin_k_mcg,
  vitamin_e_iu,
  thiamin_mg,
  riboflavin_mg,
  niacin_mg,
  vitamin_b6_mg,
  folate_mcg,
  fat_trans_g,
  fat_sat_g,
  fat_mono_g,
  fat_poly_g,
  caffeine_mg,
  copper_mg,
  folic_acid_mcg,
  energy_kcal,
  cholesterol_mg
) FROM '/Nutrients.csv' CSV HEADER;"

NUTRITION_CSV_SCRIPT=$(cat <<EOF
import csv

csv_filter = [
    "Protein",
    "Total lipid (fat)",
    "Carbohydrate, by difference",
    "Ash",
    "Water",
    "Sugars, total",
    "Fiber, total dietary",
    "Fiber, soluble",
    "Fiber, insoluble",
    "Calcium, Ca",
    "Iron, Fe",
    "Magnesium, Mg",
    "Phosphorus, P",
    "Potassium, K",
    "Sodium, Na",
    "Zinc, Zn",
    "Manganese, Mn",
    "Vitamin A, IU",
    "Vitamin D",
    "Vitamin C, total ascorbic acid",
    "Vitamin K (phylloquinone)",
    "Vitamin E",
    "Thiamin",
    "Riboflavin",
    "Niacin",
    "Vitamin B-6",
    "Folate, total",
    "Cholesterol",
    "Fatty acids, total trans",
    "Fatty acids, total saturated",
    "Fatty acids, total monounsaturated",
    "Fatty acids, total polyunsaturated",
    "Energy",
    "Copper, Cu",
    "Caffeine",
    "Folic acid",
]

csv_headers = [
    "usda_id",
    "protein_g",
    "total_lipid_g",
    "carbohydrate_g",
    "ash_g",
    "water_g",
    "sugars_g",
    "total_fiber_g",
    "solu_fiber_g",
    "insolu_fiber_g",
    "calcium_mg",
    "iron_mg",
    "magnesium_mg",
    "phosphorus_mg",
    "potassium_mg",
    "sodium_mg",
    "zinc_mg",
    "manganese_mg",
    "vitamin_a_iu",
    "vitamin_d_iu",
    "vitamin_c_mg",
    "vitamin_k_mcg",
    "vitamin_e_iu",
    "thiamin_mg",
    "riboflavin_mg",
    "niacin_mg",
    "vitamin_b6_mg",
    "folate_mcg",
    "fat_trans_g",
    "fat_sat_g",
    "fat_mono_g",
    "fat_poly_g",
    "caffeine_mg",
    "copper_mg",
    "folic_acid_mcg",
    "energy_kcal",
    "cholesterol_mg",
    "alcohol_mg",
]

ano_dict =  {k: v for v, k in enumerate(csv_filter)}

# array for the fields
new_row = [0] * 37
old_name = ""
objs = []

with open("/Nutrients.csv", newline="") as csv_file:
    reader = csv.DictReader(csv_file)

    for row in reader:
        new_name = row["NDB_No"]

        if new_name != old_name:
            new_row[0] = int(new_name)
            objs.append(new_row)
            new_row = [0] * 37

        if row["Nutrient_name"] in csv_filter:
            new_row[ano_dict.get(row["Nutrient_name"])] = float(row["Output_value"])

        old_name = new_name

with open("/var/lib/postgresql/seed.csv", "w", newline="") as myFile:
    wr = csv.writer(myFile, quoting=csv.QUOTE_NONE)
    wr.writerow(csv_headers)
    for row in objs:
        wr.writerow(row)
EOF
)

# Create and import nutrition CSV File
python3 -c "${NUTRITION_CSV_SCRIPT}"
# psql -v ON_ERROR_STOP=1 "${COPY_COMMAND}"

echo "Seeding Successful"
