# MVI Pattern
**Model(Repository)** : 데이터를 로드하고, 비즈니스 로직을 처리  
**View** : UI 관리, ViewModel 상태를 관찰해서 UI 업데이트  
**ViewModel** : Intent 처리, 비즈니스 로직 실행하고, 결과를 State로 변환해서 View로 전달  
**Intent** : 사용자의 액션을 나타내고, View에서 ViewModel로 전달  
**State** : 상태를 나타내고, ViewModel에서 View로 전달

<br>

# 흐름으로 이해하기

### 사용자 입력 (Intent 생성)
- 이벤트 트리거 -> loadImage() 메서드 호출
- loadImage()는 viewModel.channel.send()를 통해 LoadImage Intent를 ViewModel에 보냄

### Intent 처리 (ViewModel)
- ViewModel은 channel.consumeAsFlow()로 LoadImage Intent를 수신함.
- LoadImage Intent를 처리하여 이미지를 로드하기 위해 loadImage() 메서드를 호출.

### 데이터 로드 (Repository 호출)
- loadImage() 메서드는 imageRepository.getRandomImage()를 호출하여 이미지를 로드함.

### 상태 업데이트 (ViewModel -> View)
- state는 StateFlow를 통해 View에 전달됨.
- MviActivity는 state.collectLatest로 상태 변경을 관찰함.
- 상태가 MviState.LoadedImage로 변경되면 이미지를 표시하고, MviState.Error가 발생하면 스낵바.

