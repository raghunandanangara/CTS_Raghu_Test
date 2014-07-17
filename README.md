CTS_Raghu_Test
==============

Notes:

1. Task of "Lazy Downloads of images on demand" for Listview was accomplished using Google's Volley Library.
2. I am not allocating space to show a Image when there is no URL in imageHref field as per specifications
3. I have used 2 more images for displaying images in Listview
   a) If the image is being downloaded, then "no_image.png" is displayed until original image download is completed
   b) If original image has failed to download(Reason can be anything eg:403 HTTP error), then "error_image1.jpg" is displayed
4. Used facts.json file as raw file to extract JSON
5. Title of the App is dynamically updated using parsed JSON data
6. Refresh button will parse the JSON file and if there is a change, then it only it updates the Listview.
