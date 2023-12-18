# image-text-extractor

Backend app functionality required:
1. imageTextExtraction lib: Image text extraction (imageTextExtraction lib)
    1. Write as Kotlin module (lib)
2. dataSetCreator lib: Convert extracted text (array) to a CSV/TSV training dataset
    1. Write as Kotlin module (lib)
    2. Will need to input a label manually to the CSV file
3. retrainBertModelForEvents app: Training BERT model with CSV/TSV to create a TensorFlow Lite model
    1. Write as Python app using MediaPipe
4. classifyEventText lib:
    1. Write as Kotlin module (lib) that wraps Media Pipe TextClassifier
5. eventUploader lib:
    1. Uploads data to database 
6. eventsBackend app: Import the TensorFlow Lite model, import the imageTextExtraction lib, takes an input image, extracts the text using imageTextExtraction lib, classifies text from new images you input using Media Pipe TextClassifier using classifyEventText lib, uploads this newly classified data to a database using eventUploader lib
    1. Write as Android app
