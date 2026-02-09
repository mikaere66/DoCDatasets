# DoC Datasets

The DoC Datasets app provides access to 34 different publicly
available New Zealand Department of Conservation APIs, with
information about a wide variety of DoC assets and resources.

Although historical data is included (see res/raw), updated data is sourced
from ArcGIS servers in the .geojson format, and processed within the app using
Kotlin Serialization. An included Room database stores data-types/dataset basics.

For this app to function **fully**, you will need to provide *your own*
API key from [Google Maps](https://developers.google.com/maps/get-started)