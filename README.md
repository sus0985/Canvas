# Canvas
### 동작
- 화면을 클릭하여 드래그하면 임의의 색상과 투명도로 사각형을 원하는 크기만큼 그릴 수 있다

### 구현 방법
- MVC패턴
  - Model: 그리는 사각형에 대한 정보
  - View: Custom하게 된 View
  - Controller: Activity
 
- Class에 View를 상속하여 Custom하였다.
- 첫 클릭 시 입력받은 좌표로 Entity를 만들고 드래그하며 Size를 변경한 후 invalidate()하여 Canvas에 사각형을 그려주었다.

### 동작 화면
https://user-images.githubusercontent.com/83066991/134115691-d1696005-872e-45a0-8adf-48db0681a721.mov
