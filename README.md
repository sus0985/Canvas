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
### [작업일: 21년 9월 21일 월요일]
---

## 추가 작업
- Floating Action Button을 추가하여 사각형을 그릴지, 그리지 않을지 선택할 수 있도록 했다.
- Canvas에 interface로 navigator를 만들어 view와 model을 좀 더 명확히 구분할 수 있었다.
```kotlin
interface CanvasNavigator {
  fun updateCanvas(canvas: Canvas)
}
```
- Custom한 View 내에 interface 변수를 선언해두고, Activity에 상속시켜 함수를 override 하였다.
```kotlin
class CanvasView(context: Context, attr: AttributeSet?) : View(context, attr) {
  private lateinit var canvasNavigator: CanvasNavigator
  
  fun setCanvasNavigator(canvasNavigator: CanvasNavigator) {
    this.canvasNavigator = canvasNavigator
  }
  
  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas ?: return
    
    canvasNavigator.updateCanvas(canvas)
  }
}
```
- 이렇게 만들면서 model 내에 있던 사각형을 그리는 함수 drawShape를 Controller로 이동시킬 수 있었다.
```kotlin
class MainActivity : AppCompatActivity(), CanvasNavigator {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //...
    binding.canvas.setCanvasNavigator(this)
    //...
  }
}
```
### 동작 화면
https://user-images.githubusercontent.com/83066991/134386718-d345210a-8011-4cb3-a143-785a70abc6e1.mov

### [작업일: 21년 9월 23일 목요일]
---
## 추가 작업
- 사진을 선택하여 원하는 크기만큼 붙여넣는 동작 구현
- Text 클래스를 Pen으로 변경하여 펜으로 그리는 동작 구현

### 동작 화면
https://user-images.githubusercontent.com/83066991/134640267-474c45c2-9c55-457f-9f2c-3eee3c87a501.mov

### [작업일: 21년 9월 24일 금요일]
---
## 추가 작업
- 멀티 터치 시 생성한 Shape을 옮기는 동작 구현

### 동작 화면
https://user-images.githubusercontent.com/83066991/135494768-0144550a-8f4e-4d15-b60d-0bdec0720729.mov
