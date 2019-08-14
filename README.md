
# Rick And Morty Fan Made App


*Note that* this app uses  [Rick And Morty Api](https://rickandmortyapi.com/). 
Check it out if you are looking for a more information.

---
## Libs
- [Dagger](https://github.com/google/dagger)
- [Shape of view](https://github.com/florent37/ShapeOfView)
- [Fresco](https://github.com/facebook/fresco)
- [Retrofit with coroutines adapter](https://github.com/square/retrofit)
- [SmoothProgressBar](https://github.com/castorflex/SmoothProgressBar)

---
This project is based in [Android Jetpack](https://developer.android.com/jetpack?hl=es-419) using MVVM pattern, LiveData and ViewModel

![MVVM pattern](https://miro.medium.com/max/909/1*BpxMFh7DdX0_hqX6ABkDgw.png)
*Image from: [This medium post](https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-1-604f50cda1)*

Networking and asynchronus work are done with kotlin coroutines a great way to write simple code :D

![enter image description here](https://miro.medium.com/max/1050/1*OEX52nKgM1SHGO4l1mvV1A.gif)
*Gif from [this kotlin coroutines post](https://proandroiddev.com/how-to-make-sense-of-kotlin-coroutines-b666c7151b93)*

---
## About project
This project is an example about how to do a network call and show that information to the user in a clean way
### Features
- List of Rick and Morty characters
- Pagination

![list](demo/character_list.gif)
- Filter list by name, gender, etc. (we use [DiffUtils](https://developer.android.com/reference/android/support/v7/util/DiffUtil) to manage the list)

![list](demo/filter.gif)
- Detail of character
- Share of Image to any external application
- Smooth drag and drop

![list](demo/drag_and_move.gif)

### Demo APK
[You can find it here :D](demo/demo.apk)
