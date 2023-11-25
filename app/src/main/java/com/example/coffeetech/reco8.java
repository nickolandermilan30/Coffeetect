package com.example.coffeetech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class reco8 extends AppCompatActivity {

    private TextView titleTextView;
    private ScrollView paragraphScrollView;
    private TextView paragraphTextView;
    private ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco8);

        imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.image, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imagee, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imageee, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        titleTextView = findViewById(R.id.titleTextView);
        paragraphScrollView = findViewById(R.id.paragraphScrollView);
        paragraphTextView = findViewById(R.id.paragraphTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve the disease name passed from RecommendationActivity
            String diseaseName = intent.getStringExtra("diseaseName");

            // Set the most frequent disease as the title
            titleTextView.setText(diseaseName);

            // Use the disease name to fetch and display relevant recommendations
            if (paragraphTextView != null) {
                displayRecommendationsForDisease(diseaseName);
            } else {
                // Handle the case where paragraphTextView is null
                // Log an error, show a message, or take appropriate action
            }
        }
    }

    private void displayRecommendationsForDisease(String diseaseName) {
        String recommendation;

        switch (diseaseName) {
            case "Healthy Leaf":
                recommendation = "Recommendation for Healthy Leaf: Keep up with good plant care practices.";
                break;
            case "Phoma Mild":
                recommendation = "Phoma leaf spot is a fungal disease that affects coffee plants and can result in leaf damage, potentially impacting coffee yields. In mild conditions, implementing the following measures can help manage the disease effectively:\n" +
                        "\n" +
                        "Phoma leaf spot can be addressed in mild conditions through the following steps:\n" +
                        "\n" +
                        "Prune and Remove Affected Leaves:\n" +
                        "Description: Promptly identify and prune leaves showing signs of infection.\n" +
                        "Action: Remove infected leaves as soon as they are noticed to prevent the disease from spreading to other parts of the plant.\n" +
                        "Rationale: Eliminating infected leaves reduces the risk of further contamination.\n" +
                        "\n" +
                        "Enhance Plant Nutrition:\n" +
                        "Description: Ensure the coffee plants receive optimal nutrients, including phosphorus, potassium, and micronutrients.\n" +
                        "Action: Regularly fertilize the plants to maintain their health and enhance resistance to diseases.\n" +
                        "Rationale: Well-nourished plants are more robust and less susceptible to infections.\n" +
                        "\n" +
                        "Monitor Plant Health:\n" +
                        "Description: Regularly inspect the plants for signs of disease or stress.\n" +
                        "Action: Monitor for symptoms such as spotting or discoloration on the leaves, which may indicate the presence of Phoma leaf spot.\n" +
                        "Rationale: Early detection enables timely intervention and prevents the disease from becoming severe.\n" +
                        "\n" +
                        "Consult with Experts:\n" +
                        "Description: Seek professional advice from agricultural specialists or plant pathologists.\n" +
                        "Action: Consult with experts to accurately diagnose the disease and receive guidance on appropriate treatment options.\n" +
                        "Rationale: Experts can provide insights into the specific conditions and recommend targeted treatments for Phoma leaf spot.\n" +
                        "\n" +
                        "Note:\n" +
                        "It is crucial to consult with agricultural specialists or plant pathologists for an accurate diagnosis of the disease and to determine the most suitable treatment options for your specific conditions. Early intervention and a proactive approach are essential to effectively manage Phoma leaf spot in coffee plants.\n" +
                        "\n";
                break;
            case "Phoma Moderate":
                recommendation = "Phoma is a fungal disease that can impact coffee plants, potentially leading to significant damage to the leaves and a subsequent reduction in coffee yields. In moderate conditions, a combination of cultural and chemical control methods can be employed to address the issue.\n" +
                        "\n" +
                        "To manage Phoma effectively under moderate severity conditions, consider the following steps:\n" +
                        "\n" +
                        "Remove and Dispose of Infected Leaves:\n" +
                        "Action: Promptly prune and remove any leaves showing signs of Phoma infection.\n" +
                        "Rationale: Removing infected leaves is crucial for preventing the spread of the disease to other parts of the plant. Ensure that the removed leaves are adequately destroyed to prevent further contamination.\n" +
                        "\n" +
                        "Application of Fungicides:\n" +
                        "Action: Apply appropriate fungicides to control the spread of Phoma.\n" +
                        "Considerations: The choice of fungicide should be based on the severity of the infection and the crop's growth stage. Seek advice from an agricultural specialist to determine the most suitable fungicide and the correct application method.\n" +
                        "\n" +
                        "Optimize Plant Nutrition:\n" +
                        "Action: Ensure that coffee plants receive the necessary nutrients, including phosphorus, potassium, and micronutrients.\n" +
                        "Rationale: Well-nourished plants are more resistant to diseases. Maintaining optimal plant nutrition helps strengthen the plant's natural defenses against Phoma.\n" +
                        "\n" +
                        "Regular Plant Monitoring:\n" +
                        "Action: Implement a routine monitoring schedule to detect signs of Phoma early on.\n" +
                        "Rationale: Regular monitoring allows for the early identification and treatment of the disease before it escalates. Look for symptoms such as lesions or discoloration on the leaves.\n" +
                        "\n" +
                        "Important Note:\n" +
                        "It is crucial to consult with agricultural specialists or plant pathologists for an accurate diagnosis of the Phoma infection and to determine the most appropriate treatment options. Their expertise will help tailor the management strategy to the specific conditions and severity of the disease in your coffee plants.\n" +
                        "\n" +
                        "By following these recommendations and seeking professional guidance, you can effectively manage Phoma under moderate conditions and safeguard the health of your coffee plants.\n";
                break;
            case "Phoma Critical":
                recommendation = "Phoma, a fungal disease affecting coffee plants, poses a serious threat to crop health, potentially leading to significant yield reduction. Addressing this critical severity requires a comprehensive approach, combining cultural practices and chemical interventions.\n" +
                        "\n" +
                        "To effectively manage Phoma in coffee plants, consider the following recommendations:\n" +
                        "\n" +
                        "Prompt Removal of Infected Tissue:\n" +
                        "Act swiftly to prune and eliminate any infected leaves upon detection.\n" +
                        "Thoroughly dispose of the removed foliage to prevent further contamination.\n" +
                        "\n" +
                        "Strategic Fungicide Application:\n" +
                        "Employ fungicides to curtail the disease's progression.\n" +
                        "Choose an appropriate fungicide based on the infection's severity and the crop's developmental stage.\n" +
                        "Seek guidance from agricultural specialists to determine the most effective fungicide and application method.Optimize Plant Nutrition:\n" +
                        "Ensure a well-balanced nutrient supply, including phosphorus, potassium, and micronutrients.\n" +
                        "Promote plant health through optimal nutrition, enhancing resistance to diseases such as Phoma.\n" +
                        "\n" +
                        "Regular Plant Monitoring:\n" +
                        "Implement a consistent monitoring regimen to detect signs of Phoma or any other stress factors.\n" +
                        "Look for indicators like yellow spots on leaves and take immediate action upon identification.\n" +
                        "\n" +
                        "Important Note:\n" +
                        "It is imperative to engage with agricultural specialists or plant pathologists for an accurate diagnosis of Phoma and to receive tailored treatment recommendations. Their expertise will guide you in making informed decisions for effective disease management in your coffee plants. Early intervention and a proactive approach are crucial in mitigating the impact of Phoma on your crop.\n" +
                        "\n" +
                        "Remember, a collaborative effort between farmers and agricultural experts is vital to address the unique conditions of each affected plantation and ensure the successful implementation of control measures.\n" +
                        "\n";
                break;
            case "Cercospora Mild":
                recommendation = "For a mild severity of Cercospora leaf spot on coffee plants, you can consider the following recommendations:\n" +
                        "\n" +
                        "Cercospora leaf spot is a fungal disease that affects coffee plants, manifesting as small, dark spots on the leaves. In mild conditions, early intervention and proper care can effectively manage the disease. Follow these guidelines:\n" +
                        "\n" +
                        "Prune and Remove Infected Leaves:\n" +
                        "Inspect your coffee plants regularly and promptly remove any leaves showing signs of Cercospora infection.\n" +
                        "Prune affected branches and dispose of infected leaves properly to prevent the spread of spores.\n" +
                        "\n" +
                        "Apply Fungicides as Preventive Measures:\n" +
                        "Consider applying fungicides that are effective against Cercospora leaf spot. Choose a suitable fungicide based on the severity of the infection and the growth stage of the coffee plants.\n" +
                        "Consult with an agricultural specialist to determine the right fungicide and application method for your specific conditions.\n" +
                        "\n" +
                        "Enhance Plant Nutrition:\n" +
                        "Maintain a balanced and nutrient-rich soil environment by providing essential nutrients such as phosphorus, potassium, and micronutrients.\n" +
                        "Healthy and well-nourished plants are better equipped to resist diseases, including Cercospora leaf spot.\n" +
                        "\n" +
                        "Implement Good Agricultural Practices:\n" +
                        "Practice proper spacing between coffee plants to ensure good air circulation and reduce humidity, creating an unfavorable environment for fungal growth.\n" +
                        "Use mulch around the base of plants to prevent soil-borne infections and maintain consistent moisture levels.\n" +
                        "\n" +
                        "Regular Monitoring and Early Detection:\n" +
                        "Regularly monitor your coffee plants for any signs of Cercospora leaf spot, such as small circular lesions on the leaves.\n" +
                        "Early detection enables timely intervention and reduces the impact of the disease.\n" +
                        "\n" +
                        "Consult with Agricultural Experts:\n" +
                        "It is crucial to seek advice from agricultural specialists or plant pathologists for an accurate diagnosis and tailored treatment options.\n" +
                        "Experts can provide specific insights into the local conditions and recommend appropriate measures for managing Cercospora leaf spot effectively.\n" +
                        "\n" +
                        "Note: Adapt these recommendations based on your local climate, coffee plant varieties, and specific conditions. Swift and informed action is key to preventing the escalation of Cercospora leaf spot in your coffee plantation.\n" +
                        "\n";
                break;
            case "Cercospora Moderate":
                recommendation = "Cercospora leaf spot is a fungal disease that can moderately affect coffee plants, potentially reducing yields. In such conditions, a strategic combination of cultural and chemical control methods is recommended.\n" +
                        "\n" +
                        "To effectively address Cercospora leaf spot in moderate conditions, consider implementing the following measures:\n" +
                        "\n" +
                        "Prompt Removal of Infected Leaves:\n" +
                        "Identify and promptly remove any leaves showing signs of Cercospora infection. Early removal helps prevent the spread of the disease to healthy parts of the plant.\n" +
                        "Prune and destroy infected leaves to minimize the risk of further contamination.\n" +
                        "\n" +
                        "Application of Fungicides:\n" +
                        "Utilize fungicides to control the progression of Cercospora leaf spot. Consult with an agricultural specialist to determine the most suitable fungicide based on the severity of the infection and the crop's developmental stage.\n" +
                        "Adhere strictly to recommended application methods and dosage.\n" +
                        "\n" +
                        "Optimize Plant Nutrition:\n" +
                        "Maintain proper plant nutrition by ensuring the coffee plants receive essential nutrients such as phosphorus, potassium, and micronutrients.\n" +
                        "Well-nourished plants are more resilient to diseases, including Cercospora leaf spot.\n" +
                        "\n" +
                        "Regular Plant Monitoring:\n" +
                        "Conduct regular and thorough monitoring of the coffee plants. Look for early signs of Cercospora leaf spot, such as the presence of lesions or spots on the leaves.\n" +
                        "Early detection allows for timely intervention and minimizes the risk of severe infection.\n" +
                        "It is crucial to emphasize that an accurate diagnosis of Cercospora leaf spot and tailored treatment recommendations should be sought from agricultural specialists or plant pathologists. Their expertise ensures precise identification of the disease and the implementation of effective treatment strategies.\n" +
                        "\n" +
                        "Note: The guidance provided here serves as general advice, and site-specific factors should be considered. Always consult with local agricultural experts for the most relevant recommendations based on your specific conditions.\n" +
                        "\n";
                break;
            case "Cercospora Critical":
                recommendation = "Cercospora is a fungal pathogen known to cause serious damage to various crops, including coffee plants. Addressing a Cercospora outbreak promptly and effectively is crucial to minimize its impact. Consider the following recommendations for managing Cercospora and mitigating its severity:\n" +
                        "\n" +
                        "Early Detection and Action:\n" +
                        "Cercospora infections can spread rapidly, leading to substantial crop damage. Implement a proactive monitoring system to detect signs of the disease early on. Regularly inspect the plants for symptoms such as leaf spots, discoloration, or any unusual patterns. Swiftly take action upon detection to prevent further escalation.\n" +
                        "\n" +
                        "Prompt Removal of Infected Material:\n" +
                        "Isolate and remove any plant material showing signs of Cercospora infection. Prune and discard infected leaves immediately to prevent the pathogen's spread. Proper disposal of infected material is vital in curbing the disease's progression. This step is fundamental to reducing the risk of contamination in the surrounding environment.\n" +
                        "\n" +
                        "Strategic Fungicide Application:\n" +
                        "Employ fungicides judiciously to manage Cercospora. Consult with agricultural specialists or plant pathologists to identify suitable fungicides based on the severity of the infection and the crop's developmental stage. Adhere to recommended application methods and dosage to ensure optimal effectiveness. Timely and appropriate fungicide use can significantly impede the pathogen's growth.\n" +
                        "\n" +
                        "Optimization of Plant Nutrition:\n" +
                        "Maintaining the nutritional health of coffee plants is integral to enhancing their resilience against Cercospora. Provide balanced nutrition, including essential elements such as phosphorus, potassium, and micronutrients. Well-nourished plants are better equipped to withstand disease pressures. Consider soil testing and consulting with agricultural experts to tailor a nutrition plan that bolsters plant immunity.\n" +
                        "\n" +
                        "Regular Consultation with Agricultural Specialists:\n" +
                        "Given the complexity of managing Cercospora, regular consultation with agricultural specialists or plant pathologists is paramount. Seek their expertise for accurate disease diagnosis and advice on effective treatment options. Their insights can guide decisions on fungicide selection, application timing, and overall disease management strategies.\n" +
                        "\n" +
                        "Advisory Note:\n" +
                        "This guide is intended as a general recommendation. For precise diagnosis and treatment planning, always consult with qualified agricultural specialists or plant pathologists. Their expertise ensures an accurate understanding of the specific conditions and tailored solutions for managing Cercospora effectively.\n";
                break;
            case "Leaf Miner Mild":
                recommendation = "Diseases_Information miner infestations in plants can be managed effectively through a combination of cultural and chemical control methods. Here's a recommendation for mild severity of leaf miner infestation:\n" +
                        "\n" +
                        "Prune and Remove Infested Leaves:\n" +
                        "Carefully inspect the plants for signs of leaf miner damage, such as winding tunnels or discolored trails on the leaves.\n" +
                        "Prune and remove the infested leaves as soon as you notice them. This helps prevent the spread of leaf miners to other parts of the plant.\n" +
                        "\n" +
                        "Encourage Natural Predators:\n" +
                        "Foster the presence of natural predators of leaf miners, such as parasitic wasps or predatory beetles. These insects can help keep the leaf miner population in check.\n" +
                        "\n" +
                        "Apply Neem Oil:\n" +
                        "Use neem oil, a natural and organic insecticide, to control leaf miners. Neem oil disrupts the feeding and reproductive cycles of the pests. Follow the recommended application instructions on the product.\n" +
                        "\n" +
                        "Introduce Beneficial Nematodes:\n" +
                        "Beneficial nematodes are microscopic organisms that feed on the larvae of leaf miners. Applying nematodes to the soil can help control the population of leaf miners.\n" +
                        "\n" +
                        "Monitor Plant Health:\n" +
                        "Regularly monitor the overall health of the plants. Provide appropriate nutrients, ensuring that the plants receive a balanced fertilizer. Healthy plants are better able to resist and recover from pest infestations.\n" +
                        "\n" +
                        "Use Yellow Sticky Traps:\n" +
                        "Place yellow sticky traps near the affected plants. Diseases_Information miners are attracted to the color yellow, and the traps can help reduce their numbers.\n" +
                        "\n" +
                        "Rotate Crops:\n" +
                        "If possible, practice crop rotation to disrupt the life cycle of leaf miners. Avoid planting the same crops in the same location in consecutive growing seasons.\n" +
                        "\n" +
                        "Consult with a Horticulturist:\n" +
                        "Seek advice from a horticulturist or pest control professional for specific recommendations based on the type of leaf miner and the plants affected.\n" +
                        "\n" +
                        "Remember to document the severity of the infestation and the effectiveness of the control measures. If the infestation persists or worsens, it is advisable to consult with a local agricultural extension office or plant health expert for a more tailored and effective control plan.\n";
                break;
            case "Leaf Miner Moderate":
                recommendation = "Diseases_Information miner infestations in coffee plants can have a detrimental impact on plant health and productivity. In moderate conditions, here are recommendations for managing leaf miner issues:\n" +
                        "\n" +
                        "Identification and Removal:\n" +
                        "Inspect Leaves Regularly: Periodically examine the coffee plant leaves for signs of leaf miner infestation. Look for serpentine mines, blotches, or discolored streaks on the leaves.\n" +
                        "Prune Affected Areas: Immediately prune and remove leaves that show visible signs of leaf miner damage. This helps prevent the spread of the infestation to other parts of the plant.\n" +
                        "\n" +
                        "Chemical Control:\n" +
                        "Selective Pesticides: Consult with an agricultural specialist to choose an appropriate pesticide. Selective pesticides that target leaf miners while minimizing harm to beneficial insects are preferable.\n" +
                        "Timely Application: Apply pesticides at the recommended times based on the life cycle of the leaf miners. This helps maximize effectiveness while minimizing environmental impact.\n" +
                        "Follow Safety Guidelines: Adhere to safety guidelines and recommended concentrations when applying pesticides. Use protective gear and consider environmentally friendly options.\n" +
                        "\n" +
                        "Cultural Practices:\n" +
                        "Crop Rotation: If possible, practice crop rotation to disrupt the life cycle of leaf miners. Avoid planting coffee in the same location consecutively to reduce the risk of recurring infestations.\n" +
                        "Mulching: Apply organic mulch around the base of coffee plants. Mulch helps retain moisture, regulates soil temperature, and may discourage leaf miners from laying eggs.\n" +
                        "\n" +
                        "Enhance Plant Health:\n" +
                        "Optimize Nutrition: Provide the coffee plants with balanced nutrition, including essential macro and micronutrients. A well-nourished plant is better equipped to resist and recover from pest attacks.\n" +
                        "Proper Watering: Maintain appropriate soil moisture levels. Avoid overwatering, as excessively moist conditions can attract leaf miners.\n" +
                        "\n" +
                        "Monitoring and Early Intervention:\n" +
                        "Regular Surveillance: Conduct routine monitoring of the coffee plantation for any signs of leaf miner activity. Look for characteristic trails and tunnels on leaves.\n" +
                        "Early Treatment: If leaf miners are detected, take prompt action to prevent the infestation from becoming severe. Early intervention is crucial for effective control.\n" +
                        "\n" +
                        "Note: For accurate diagnosis and tailored treatment options, it is imperative to consult with an agricultural specialist or a plant pathologist. Their expertise will ensure a precise understanding of the leaf miner issue and the implementation of appropriate control measures.\n" +
                        "\n" +
                        "\n";
                break;
            case "Leaf Miner Critical":
                recommendation = "Diseases_Information miners are pests that can significantly impact plant health by tunneling through leaves, leading to visible damage. In the case of leaf miners affecting your plants, consider the following recommendations for critical conditions:\n" +
                        "\n" +
                        "Early Detection and Removal:\n" +
                        "Regularly inspect the leaves for signs of infestation, such as serpentine tunnels or blotches.\n" +
                        "Promptly remove and destroy infested leaves to prevent the spread of the pests to other parts of the plant.\n" +
                        "\n" +
                        "Beneficial Insects:\n" +
                        "Introduce natural predators of leaf miners, such as parasitic wasps or predatory beetles, to help control the population.\n" +
                        "\n" +
                        "Neem Oil Application:\n" +
                        "Apply neem oil, a natural insecticide, following the manufacturer's instructions. Neem oil disrupts the feeding and reproductive cycles of leaf miners.\n" +
                        "\n" +
                        "Sticky Traps:\n" +
                        "Place yellow sticky traps near the plants to attract and trap adult leaf miners. This helps reduce the overall population.\n" +
                        "\n" +
                        "Cultural Practices:\n" +
                        "Implement good cultural practices, including proper spacing between plants, to reduce humidity and create an environment less favorable for leaf miner development.\n" +
                        "\n" +
                        "Mulching:\n" +
                        "Apply a layer of organic mulch around the base of plants to discourage adult leaf miners from laying eggs in the soil.\n" +
                        "\n" +
                        "Avoid Over-Fertilization:\n" +
                        "Avoid excessive use of nitrogen-rich fertilizers, as this can attract leaf miners. Opt for a balanced fertilizer to maintain plant health.\n" +
                        "\n" +
                        "Natural Repellents:\n" +
                        "Consider natural repellents such as garlic or onion sprays to deter leaf miners. These can be applied according to product instructions.\n" +
                        "\n" +
                        "Companion Planting:\n" +
                        "Explore companion planting strategies, as certain plants may help repel leaf miners from the main crop.\n" +
                        "\n" +
                        "Regular Monitoring:\n" +
                        "Continuously monitor the plants for any resurgence of leaf miners, especially during peak infestation seasons.\n" +
                        "\n" +
                        "Note: For severe infestations or if these methods prove insufficient, consult with a local agricultural extension service or entomologist for tailored advice and, if necessary, chemical treatment options. Early intervention and a combination of these methods will contribute to effective leaf miner management.\"\n" +
                        "\n" +
                        "Always adapt recommendations based on the specific conditions and plant species affected, and consider the environmental impact of any control methods used.\n" +
                        "\n";
                break;
            case "Leaf Rust Mild":
                recommendation = "Coffee leaf rust is a fungal disease that poses a threat to coffee plants, potentially leading to a decline in yields. In cases of mild severity, implementing the following measures can help manage and mitigate the impact of leaf rust on coffee plants:\n" +
                        "\n" +
                        "Early Detection and Removal of Infected Leaves:\n" +
                        "Regularly inspect the coffee plants for any signs of leaf rust.\n" +
                        "Immediately remove and prune leaves that show symptoms of infection.\n" +
                        "Prompt removal helps prevent the spread of the disease to healthier parts of the plant.\n" +
                        "\n" +
                        "Fungicide Application:\n" +
                        "Consider using appropriate fungicides to control the spread of coffee leaf rust.\n" +
                        "Consult with an agricultural specialist to determine the most effective fungicide for the specific severity of the infection and the growth stage of the crop.\n" +
                        "Follow recommended application methods and dosages for optimal results.\n" +
                        "\n" +
                        "Optimize Plant Nutrition:\n" +
                        "Maintain a well-balanced nutrient supply for the coffee plants, including essential elements such as phosphorus, potassium, and micronutrients.\n" +
                        "Healthy and well-nourished plants are better equipped to resist diseases, including leaf rust.\n" +
                        "\n" +
                        "Regular Monitoring and Early Intervention:\n" +
                        "Establish a routine monitoring schedule to closely observe the coffee plants.\n" +
                        "Look for early signs of stress or disease, such as the development of yellow spots on the leaves.\n" +
                        "Swiftly intervene and apply appropriate treatments if any signs of leaf rust are detected.\n" +
                        "\n" +
                        "It is crucial to emphasize the importance of consulting with agricultural specialists or plant pathologists for an accurate diagnosis of the disease and tailored treatment recommendations. Each case of coffee leaf rust may require specific considerations based on the severity of the infection and the unique characteristics of the coffee plants. Early and informed action is key to managing leaf rust effectively and preserving the health of coffee crops.\n" +
                        "\n" +
                        "\n";
                break;
            case "Leaf Rust Moderate":
                recommendation = "Coffee leaf rust is a fungal disease that affects coffee plants and can lead to significant damage to the leaves, resulting in decreased coffee yields. In moderate conditions, an effective approach to managing leaf rust involves a combination of cultural and chemical control methods.\n" +
                        "\n" +
                        "To address coffee leaf rust in moderate conditions, consider the following recommendations:\n" +
                        "\n" +
                        "Remove and Destroy Infected Leaves:\n" +
                        "Act promptly by pruning and removing any leaves showing signs of infection.\n" +
                        "Immediate removal helps prevent the further spread of the disease to other parts of the plant.\n" +
                        "Ensure the proper disposal of infected leaves to prevent contamination.\n" +
                        "\n" +
                        "Apply Fungicides:\n" +
                        "Utilize fungicides to control the progression of the disease.\n" +
                        "Consult with an agricultural specialist to identify the most suitable fungicide based on the severity of the infection and the crop's growth stage.\n" +
                        "Follow recommended application methods for optimal effectiveness.\n" +
                        "\n" +
                        "Maintain Good Plant Nutrition:\n" +
                        "Provide the coffee plants with appropriate nutrients, including phosphorus, potassium, and micronutrients.\n" +
                        "Well-nourished plants are generally more resilient to diseases like coffee leaf rust.\n" +
                        "Ensure a balanced and consistent fertilization program to promote overall plant health.\n" +
                        "\n" +
                        "Regularly Monitor the Plant:\n" +
                        "Implement a routine monitoring schedule to detect signs of disease or stress early on.\n" +
                        "Look for indicators such as yellow spots on the leaves, which may signal the presence of coffee leaf rust.\n" +
                        "Early detection allows for timely intervention and reduces the risk of severe infections.\n" +
                        "\n" +
                        "Note:\n" +
                        "It is crucial to seek professional advice from an agricultural specialist or a plant pathologist for an accurate diagnosis of the disease and to determine the most appropriate treatment options. Their expertise will ensure that the chosen strategies align with the specific conditions of the coffee plantation and the characteristics of the leaf rust infection. Regular collaboration with specialists enhances the effectiveness of the management plan and contributes to the overall health and productivity of the coffee plants.\n" +
                        "\n";
                break;
            case "Leaf Rust Critical":
                recommendation = "Coffee leaf rust (CLR) is a devastating fungal disease that poses a serious threat to coffee plants. To effectively manage and mitigate the impact of CLR, particularly in conditions where the severity is critical, it is crucial to adopt a comprehensive approach. Below is a detailed recommendation:\n" +
                        "\n" +
                        "Coffee Diseases_Information Rust Management in Critical Conditions:\n" +
                        "\n" +
                        "Prompt Removal of Infected Leaves:\n" +
                        "Description: Immediately identify and remove leaves showing signs of infection.\n" +
                        "Action Steps:\n" +
                        "Regularly inspect coffee plants for early signs of leaf rust.\n" +
                        "Promptly prune and discard infected leaves to prevent the spread of the disease.\n" +
                        "\n" +
                        "Strategic Fungicide Application:\n" +
                        "Description: Implement a targeted fungicide treatment plan for effective disease control.\n" +
                        "Action Steps:\n" +
                        "Consult with an agricultural specialist for a precise diagnosis.\n" +
                        "Select and apply appropriate fungicides based on severity and crop stage.\n" +
                        "Follow recommended application methods for optimal effectiveness.\n" +
                        "Adhere strictly to safety guidelines during fungicide application.\n" +
                        "\n" +
                        "Optimization of Plant Nutrition:\n" +
                        "Description: Ensure coffee plants receive optimal nutrients to enhance their natural resistance.\n" +
                        "Action Steps:\n" +
                        "Regularly assess soil nutrient levels.\n" +
                        "Supplement with phosphorus, potassium, and micronutrients as needed.\n" +
                        "Collaborate with agricultural experts to formulate a balanced nutrition plan.\n" +
                        "\n" +
                        "Regular Monitoring and Early Detection:\n" +
                        "Description: Establish a routine monitoring system to identify and address issues promptly.\n" +
                        "Action Steps:\n" +
                        "Conduct frequent inspections for any signs of disease or stress.\n" +
                        "Train farm personnel to recognize symptoms and report findings.\n" +
                        "Use advanced technologies like remote sensing for comprehensive monitoring.\n" +
                        "\n" +
                        "Expert Consultation:\n" +
                        "Description: Seek professional advice from agricultural specialists or plant pathologists.\n" +
                        "Action Steps:\n" +
                        "Consult with experts for accurate disease diagnosis.\n" +
                        "Collaborate with specialists to tailor a treatment plan based on specific conditions.\n" +
                        "Stay informed about the latest research and advancements in CLR management.\n" +
                        "\n" +
                        "Note: This comprehensive strategy aims to minimize the impact of coffee leaf rust under critical conditions. Given the complexity of the disease, collaboration with agricultural specialists and continuous monitoring are essential components of an effective management plan. The severity of CLR can vary, so adjustments to the plan may be necessary based on the evolving situation and expert recommendations.\n" +
                        "\n";
                break;
            case "Sooty Mold Mild":
                recommendation = "Sooty mold, a fungal issue that manifests as a black, powdery coating on plant surfaces, can be managed effectively through a combination of cultural and preventive measures. Follow these steps to address mild cases:\n" +
                        "\n" +
                        "Prune and Clean:\n" +
                        "Remove affected parts: Trim and discard leaves or branches with visible sooty mold. This not only eliminates the mold source but also enhances air circulation around the plant.\n" +
                        "\n" +
                        "Insect Control:\n" +
                        "Address honeydew-producing insects: Sooty mold often develops on the sugary excretions (honeydew) of insects like aphids, scales, or whiteflies. Implement insect control measures to reduce the presence of these pests.\n" +
                        "\n" +
                        "Horticultural Oil:\n" +
                        "Apply horticultural oil: A light application of horticultural oil can help control sooty mold. The oil suffocates the insects producing honeydew and disrupts the mold's growth.\n" +
                        "\n" +
                        "Natural Predators:\n" +
                        "Encourage natural enemies: Foster populations of beneficial insects, such as ladybugs or lacewings, which feed on honeydew-producing pests. This natural control method can help prevent future infestations.\n" +
                        "\n" +
                        "Watering Practices:\n" +
                        "Adjust watering habits: Sooty mold thrives in the presence of excess moisture. Ensure proper drainage and avoid overwatering to create an environment less conducive to fungal growth.\n" +
                        "\n" +
                        "Neem Oil:\n" +
                        "Consider neem oil: Neem oil, derived from the neem tree, has antifungal properties and can be an effective, organic solution for controlling sooty mold. Follow product instructions for application.\n" +
                        "\n" +
                        "Regular Monitoring:\n" +
                        "Monitor plant health: Keep a watchful eye on your plants. Regularly inspect for signs of sooty mold, pests, or stress. Early detection allows for prompt intervention.\n" +
                        "\n" +
                        "Consult with Experts:\n" +
                        "Seek professional advice: If the issue persists or worsens, consult with a local horticulturist, agricultural extension service, or plant health expert. They can provide tailored recommendations based on your specific situation.\n" +
                        "\n" +
                        "Remember, early intervention and a holistic approach are key to managing sooty mold. Tailor these recommendations to your specific plant species and local conditions, and seek professional guidance for the best results.\n";
                break;
            case "Sooty Mold Moderate":
                recommendation = "Sooty mold is a fungal issue commonly associated with honeydew secretions from sap-sucking insects, such as aphids, scales, or whiteflies. While not directly harmful to plants, it can negatively impact their aesthetics and reduce photosynthesis. In moderate conditions, addressing sooty mold involves a combination of preventive and control measures.\n" +
                        "\n" +
                        "To manage sooty mold at a moderate severity level, consider implementing the following steps:\n" +
                        "\n" +
                        "Control Insect Infestations:\n" +
                        "Identify and control sap-sucking insects responsible for honeydew production. Use insecticidal soaps, neem oil, or other appropriate insecticides to manage the pest population.\n" +
                        "\n" +
                        "Remove Infested Plant Parts:\n" +
                        "Prune and remove heavily infested plant parts, such as leaves or branches, to eliminate the source of honeydew. This helps prevent further development of sooty mold.\n" +
                        "\n" +
                        "Promote Natural Predators:\n" +
                        "Encourage the presence of natural predators, such as ladybugs or parasitic wasps, to help control insect populations naturally.\n" +
                        "\n" +
                        "Apply Horticultural Oils:\n" +
                        "Use horticultural oils to reduce the presence of sooty mold. These oils can help suppress the development of fungal spores and provide a protective barrier on plant surfaces.\n" +
                        "\n" +
                        "Improve Air Circulation:\n" +
                        "Enhance air circulation around plants by proper spacing and pruning. Good air flow can discourage the growth of sooty mold and create an environment less favorable for fungal development.\n" +
                        "\n" +
                        "Maintain Plant Health:\n" +
                        "Ensure that plants receive adequate water, sunlight, and nutrients. Healthy plants are more resilient to pest infestations and fungal issues.\n" +
                        "\n" +
                        "Regular Monitoring:\n" +
                        "Monitor plants regularly for signs of insect infestations and sooty mold development. Early detection allows for prompt intervention and minimizes the impact on plant health.\n" +
                        "\n" +
                        "Note: It is advisable to seek guidance from a horticulturist or pest control professional for a precise diagnosis of the issue and tailored treatment recommendations.\n" +
                        "\n";
                break;
            case "Sooty Mold Critical":
                recommendation = "Sooty mold is a common issue caused by the presence of honeydew-excreting insects like aphids, scale, or whiteflies. While sooty mold itself does not harm plants, it can affect their overall health and aesthetics. To effectively manage sooty mold, follow these steps:\n" +
                        "\n" +
                        "Identify and Control Honeydew-Producing Insects:\n" +
                        "Inspect your plants regularly for aphids, scale insects, whiteflies, or other honeydew-excreting pests.\n" +
                        "Implement appropriate pest control measures, such as insecticidal soaps or neem oil, to eliminate the underlying insect infestation.\n" +
                        "\n" +
                        "Prune and Remove Infested Parts:\n" +
                        "Prune and remove heavily infested parts of the plant, such as leaves or branches with a high concentration of honeydew and sooty mold.\n" +
                        "Dispose of the infested plant material properly to prevent the spread of pests and mold.\n" +
                        "\n" +
                        "Improve Air Circulation:\n" +
                        "Ensure proper spacing between plants to promote good air circulation, reducing favorable conditions for mold growth.\n" +
                        "\n" +
                        "Apply Horticultural Oil:\n" +
                        "Horticultural oils can be applied to suffocate and control sooty mold. Follow product instructions for application rates and timing.\n" +
                        "\n" +
                        "Introduce Beneficial Insects:\n" +
                        "Consider introducing natural predators of honeydew-producing insects, such as ladybugs or lacewings, to help maintain a balanced ecosystem.\n" +
                        "\n" +
                        "Regularly Wash Plants:\n" +
                        "Wash plant surfaces with a gentle stream of water to remove honeydew and sooty mold. This can be done periodically to prevent the buildup of mold.\n" +
                        "\n" +
                        "Monitor Plant Health:\n" +
                        "Keep a watchful eye on plant health and address any signs of pest infestation promptly.\n" +
                        "\n" +
                        "Professional Consultation:\n" +
                        "If the infestation persists or worsens, consult with a professional arborist, horticulturist, or pest control specialist for expert advice and intervention.\n" +
                        "\n" +
                        "Note: It's crucial to tailor your approach based on the specific plants and pests involved. Consulting with a local horticulturist or extension service can provide region-specific guidance.\"\n";
                break;
            default:
                recommendation = "No specific recommendation available for " + diseaseName;
                break;
        }

        // Set the recommendation text in your TextView
        paragraphTextView.setText(recommendation);
    }

}