## KotlinLinkedIncreasingLineView

### Usage

#### adding dependency in app/build.gradle

```
    compile project(":linkedincreasinglineview")
```

#### adding LinkedIncreasingLineView in Activity

```
    var view : LinkedIncreasingLineView = LinkedIncreasingLineView.create(this)
```

#### adding Listener

```
    view.addCompletionListener {
         Toast.makeText(this, "advanced to stage ${it + 1}", Toast.LENGTH_SHORT).show()
    }
```


### Demo

<img src="https://github.com/Anwesh43/KotlinLinkedIncreasingLineView/blob/master/screenshots/linkedincreasingline.gif" width="600px" height="350px">