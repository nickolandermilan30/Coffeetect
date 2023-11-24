package com.example.coffeetech;



import android.graphics.Bitmap;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedResultsManager<result> {
    private static List<SavedResult> historyList = new ArrayList<>();
    private static List<SavedResult> history2List = new ArrayList<>();
    private static List<SavedResult> history3List = new ArrayList<>();
    private static List<SavedResult> history4List = new ArrayList<>();
    private static List<SavedResult> history5List = new ArrayList<>();
    private static List<SavedResult> history6List = new ArrayList<>();
    private static List<SavedResult> history7List = new ArrayList<>();
    private static List<SavedResult> history8List = new ArrayList<>();
    private static List<SavedResult> history9List = new ArrayList<>();
    private static List<SavedResult> history10List = new ArrayList<>();

    private static List<SavedResult> savedResultsList = new ArrayList<>();
    private static Map<String, Integer> diseaseFrequencies = new HashMap<>();

    public static List<SavedResult> getSavedResultsList() {
        return savedResultsList;
    }



    public static void addSavedResult(SavedResult savedResult) {
        savedResultsList.add(0, savedResult);

        String diseaseName = savedResult.getDiseaseName();

        // Update disease frequencies
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            diseaseFrequencies.put(diseaseName, diseaseFrequencies.getOrDefault(diseaseName, 0) + 1);
        }

        // If savedResultsList size exceeds 10, remove the last item
        if (savedResultsList.size() > 10) {
            savedResultsList.remove(savedResultsList.size() - 1);
        }
    }



    public static String getMostFrequentDisease() {
        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseFrequencies.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }



    public static void clearSavedResults() {
        savedResultsList.clear();
    }

    public static Bitmap getImageForMostFrequentDisease() {
        String mostFrequentDisease = SavedResultsManager.getMostFrequentDisease();
        List<SavedResult> savedResults = SavedResultsManager.getSavedResultsList();

        for (SavedResult result : savedResults) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                return result.getImageBitmap();
            }
        }
        return null;

    }

    public static void addSavedResultToHistory2(SavedResult savedResult) {
        history2List.add(0, savedResult);

        // Check if `History2` exceeds the desired limit (e.g., 10)
        if (history2List.size() > 10) {
            history2List.remove(history2List.size() - 1);

            // Move the exceeding results to History3
            if (history3List.size() < 10) {
                SavedResult removedResult = history2List.remove(history2List.size() - 1);
                history3List.add(0, removedResult);
            }
        }
    }


    public static List<SavedResult> getHistoryList() {
        return historyList;
    }
    public static List<SavedResult> getHistory2List() {
        return history2List;
    }

    public static void addSavedResultToHistory(SavedResult savedResult) {
        if (historyList.size() < 10) {
            historyList.add(savedResult);
        } else if (history2List.size() < 10) {
            history2List.add(savedResult);
        } else {
            history3List.add(savedResult);
        }
    }

    public static int getSavedResultsCount() {
        return savedResultsList.size();
    }





    public static void clearHistory2Results() {
        history2List.clear();
    }


    public static int getSavedResultsCountHistory2() {
        return history2List.size();




    }

    public static void addSavedResultToHistory3(SavedResult result) {
        if (history3List.size() < 10) {
            history3List.add(result);
        }
    }

    public static void clearHistory3Results() {
        history3List.clear();
    }
    public static List<SavedResult> getHistory3List() {
        return history3List;
    }
    public static int getSavedResultsCountHistory3() {
        return history3List.size();
    }
    public static void addSavedResultToHistory4(SavedResult result) {
        if (history4List.size() < 10) {
            history4List.add(result);
        }
    }

    public static List<SavedResult> getHistory4List() {
        return history4List;
    }
    public static void clearHistory4Results() {
        history4List.clear();
    }
    public static int getSavedResultsCountHistory4() {
        return history4List.size();
    }
    public static void addSavedResultToHistory5(SavedResult result) {
        if (history5List.size() < 10) {
            history5List.add(result);
        }
    }
    public static void clearHistory5Results() {
        history5List.clear();
    }
    public static List<SavedResult> getHistory5List() {
        return history5List;
    }
    public static int getSavedResultsCountHistory5() {
        return history5List.size();
    }




    public static int getSavedResultsCountHistory6() {
        return history6List.size();
    }
    public static void addSavedResultToHistory6(SavedResult result) {
        if (history6List.size() < 10) {
            history6List.add(result);
        }
    }
    public static void clearHistory6Results() {
        history6List.clear();
    }
    public static List<SavedResult> getHistory6List() {
        return history6List;
    }





    public static int getSavedResultsCountHistory7() {
        return history7List.size();
    }
    public static void addSavedResultToHistory7(SavedResult result) {
        if (history7List.size() < 10) {
            history7List.add(result);
        }
    }
    public static void clearHistory7Results() {
        history7List.clear();
    }
    public static List<SavedResult> getHistory7List() {
        return history7List;
    }




    public static int getSavedResultsCountHistory8() {
        return history8List.size();
    }
    public static void addSavedResultToHistory8(SavedResult result) {
        if (history8List.size() < 10) {
            history8List.add(result);
        }
    }
    public static void clearHistory8Results() {
        history8List.clear();
    }
    public static List<SavedResult> getHistory8List() {
        return history8List;
    }




    public static int getSavedResultsCountHistory9() {
        return history9List.size();
    }
    public static void addSavedResultToHistory9(SavedResult result) {
        if (history9List.size() < 10) {
            history9List.add(result);
        }
    }
    public static void clearHistory9Results() {
        history9List.clear();
    }
    public static List<SavedResult> getHistory9List() {
        return history9List;
    }




    public static int getSavedResultsCountHistory10() {
        return history10List.size();
    }
    public static void addSavedResultToHistory10(SavedResult result) {
        if (history10List.size() < 10) {
            history10List.add(result);
        }
    }
    public static void clearHistory10Results() {
        history10List.clear();
    }
    public static List<SavedResult> getHistory10List() {
        return history10List;
    }


    public static List<SavedResult> getSavedResultsFromString(String savedResultsString) {
        Gson gson = new Gson();

        // Define the type for Gson to convert the JSON string into a List of SavedResult objects
        Type listType = new TypeToken<List<SavedResult>>() {}.getType();

        List<SavedResult> savedResultsList = gson.fromJson(savedResultsString, listType);

        return savedResultsList;
    }





    public static String getMostFrequentDiseaseHistory2() {
        List<SavedResult> history2List = getHistory2List();

        // Mag-create ng isang mapa para i-track ang dami ng bawat sakit
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        // I-loop ang history2List at i-count ang bawat sakit
        for (SavedResult result : history2List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        // Hanapin ang pinakamadalas na sakit sa history2List
        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }

    public static Bitmap getImageForMostFrequentDiseaseHistory2() {
        List<SavedResult> history2List = getHistory2List();

        // Mag-create ng isang mapa para i-track ang dami ng bawat sakit
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        // I-loop ang history2List at i-count ang bawat sakit
        for (SavedResult result : history2List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        // Hanapin ang pinakamadalas na sakit sa history2List
        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        // Hanapin ang result para sa pinakamadalas na sakit sa history2List
        SavedResult mostFrequentResult = null;

        for (SavedResult result : history2List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        // Kung may natagpuang result, kunin ang image bitmap
        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }





    public static Bitmap getImageForMostFrequentDiseaseHistory3() {
        List<SavedResult> history3List = getHistory3List();

        // Mag-create ng isang mapa para i-track ang dami ng bawat sakit
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        // I-loop ang history2List at i-count ang bawat sakit
        for (SavedResult result : history3List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        // Hanapin ang pinakamadalas na sakit sa history2List
        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        // Hanapin ang result para sa pinakamadalas na sakit sa history2List
        SavedResult mostFrequentResult = null;

        for (SavedResult result : history3List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        // Kung may natagpuang result, kunin ang image bitmap
        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory3() {
        List<SavedResult> history3List = getHistory3List();


        Map<String, Integer> diseaseCountMap = new HashMap<>();


        for (SavedResult result : history3List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }

    public static Bitmap getImageForMostFrequentDiseaseHistory4() {
        List<SavedResult> history4List = getHistory4List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history4List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history4List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory4() {
        List<SavedResult> history4List = getHistory4List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history4List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }


    public static Bitmap getImageForMostFrequentDiseaseHistory5() {
        List<SavedResult> history5List = getHistory5List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history5List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history5List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory5() {
        List<SavedResult> history5List = getHistory5List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history5List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }


    public static Bitmap getImageForMostFrequentDiseaseHistory6() {
        List<SavedResult> history6List = getHistory6List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history6List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history6List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory6() {
        List<SavedResult> history6List = getHistory6List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history6List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }





    public static Bitmap getImageForMostFrequentDiseaseHistory7() {
        List<SavedResult> history7List = getHistory7List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history7List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history7List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory7() {
        List<SavedResult> history7List = getHistory7List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history7List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }



    public static Bitmap getImageForMostFrequentDiseaseHistory8() {
        List<SavedResult> history8List = getHistory8List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history8List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history8List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory8() {
        List<SavedResult> history8List = getHistory8List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history8List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }


    public static Bitmap getImageForMostFrequentDiseaseHistory9() {
        List<SavedResult> history9List = getHistory9List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history9List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history9List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory9() {
        List<SavedResult> history9List = getHistory9List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history9List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }


    public static Bitmap getImageForMostFrequentDiseaseHistory10() {
        List<SavedResult> history10List = getHistory10List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history10List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        SavedResult mostFrequentResult = null;

        for (SavedResult result : history10List) {
            if (result.getDiseaseName().equals(mostFrequentDisease)) {
                mostFrequentResult = result;
                break;
            }
        }

        if (mostFrequentResult != null) {
            return mostFrequentResult.getImageBitmap();
        }

        return null;
    }

    public static String getMostFrequentDiseaseHistory10() {
        List<SavedResult> history10List = getHistory10List();
        Map<String, Integer> diseaseCountMap = new HashMap<>();

        for (SavedResult result : history10List) {
            String diseaseName = result.getDiseaseName();
            diseaseCountMap.put(diseaseName, diseaseCountMap.getOrDefault(diseaseName, 0) + 1);
        }

        String mostFrequentDisease = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : diseaseCountMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentDisease = entry.getKey();
            }
        }

        return mostFrequentDisease;
    }



}




