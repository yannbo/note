## Mockito 教程摘要
[Refer](https://www.cnblogs.com/Ming8006/p/6297333.html)
1. 验证行为
	```
	    @Test
	    public void verify_behaviour(){
	        //模拟创建一个List对象
	        List mock = mock(List.class);
	        //使用mock的对象
	        mock.add(1);
	        mock.clear();
	        verify(mock).add(1);               //1 验证add(1)和clear()行为是否发生
	        verify(mock).clear();
	    }
	```

2. 预设期望,通过when来实现(预设即设置stub):
	+ 非异常： `when(mockObject.method()).thenReturn("hello")`
	```
	    @Test
	    public void when_thenReturn(){
	        //mock一个Iterator类
	        Iterator iterator = mock(Iterator.class);
	        //预设当iterator调用next()时第一次返回hello，第n次都返回world
	        when(iterator.next()).thenReturn("hello").thenReturn("world");        /1 构建一个stub(存根)
	        //使用mock的对象
	        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
	        //验证结果
	        assertEquals("hello world world",result);
	    }
	 ```
	 + 方法体抛出异常：  
	 	- `@Test(expected = IOException.class) `
	 	- `doThrow(new IOException()).when(mockedObject).method(); `
		 ```
		    @Test(expected = IOException.class)
		    public void when_thenThrow() throws IOException {
		        OutputStream outputStream = mock(OutputStream.class);
		        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		        //预设当流关闭时抛出异常
		        doThrow(new IOException()).when(outputStream).close();              /2 预设抛出异常
		        outputStream.close();
		    }
		```
	  + 总结：
	  	- *如果一个对象被mock，那么该对象的所有方法都被mock，当方法被调用时不会执行方法体，无论是通过`@Mock`和`mock(Object.class)` mock*;（经过测试）
	  	- String 类型返回null不是"";
	  	- Int 类型返回 0；
	  	- boolean 类型返回false;

3. RETURNS_SMART_NULLS和RETURNS_DEEP_STUBS
	+ 在创建Mock对象时，如果方法没有进行预设期望(stubbing?),在调用时会返回null,如果继续对调用结果进行方法调用时可能会抛出`NullPointerException`.
		-  List mock = mock(List.class, RETURNS_SMART_NULLS); *RETURNS_SMART_NULLS参数创建的mock对象在没有调用stubbed方法时会返回SmartNull*.
		-  List mock = mock(List.class, RETURNS_DEEP_STUBS); *RETURNS_DEEP_STUBS参数程序会自动进行mock所需的对象*.
	   
	    ```
	        public static <T> T mock(Class<T> classToMock, Answer defaultAnswer) {     /3 defaultAnswer 标识mock对象的方法没有设置期望时(即stubbed)，如果不设置默认answer时，对象会返回null。
		        return mock(classToMock, withSettings().defaultAnswer(defaultAnswer));
		    }
	    ```

4. 用spy监控真实对象
	+ Mock不是真实的对象，它只是用类型class创建了一个虚拟对象，对象的所有方法都被mock，并可以设置对象行为。
	+ Spy是一个真实的对象，但它可以设置对象的行为.使用**doReturn-when**可以避免when-thenReturn调用真实对象api。
	+ InjectMocks创建这个类对象并自动将标记@Mock,@Spy等注解的属性注入到这个对象。
	```
	@Test(expected = IndexOutOfBoundsException.class)
	    public void spy_on_real_objects(){
	        List list = new LinkedList();
	        List spy = spy(list);
	        //下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
	        //when(spy.get(0)).thenReturn(3);
	        //使用doReturn-when可以避免when-thenReturn调用真实对象api
	        doReturn(999).when(spy).get(999);           / 1
	        //预设size()期望值
	        when(spy.size()).thenReturn(100);
	        //调用真实对象的api
	        spy.add(1);
	        spy.add(2);
	        assertEquals(100,spy.size());
	        assertEquals(1,spy.get(0));
	        assertEquals(2,spy.get(1));
	        verify(spy).add(1);
	        verify(spy).add(2);
	        assertEquals(999,spy.get(999));
	        spy.get(2);
	    }
	 ```

5. 通过`thenCallRealMethod`来调用mock对象真实的方法。
```
 @Test
    public void real_partial_mock(){
        A a  = mock(A.class);
        //通过thenCallRealMethod来调用真实的api
        when(a.doSomething(anyInt())).thenCallRealMethod();      / 1
        assertEquals(999,a.doSomething(999));
    }


    class A{
        public int doSomething(int i){
            return i;
        }
    }
```

6. `reset` 重置mock，清除所有的互动和预设
```
@Test
    public void reset_mock(){
        List list = mock(List.class);          
        when(list.size()).thenReturn(10);      /1 设置期望值叫做 '预设'
        list.add(1);                           /2 调用mock对象的方法叫做 '互动'
        assertEquals(10,list.size());
        //重置mock，清除所有的互动和预设
        reset(list);                           /3 重置
        assertEquals(0,list.size());
    }
```

7. Mockito如何实现Mock
	Mockito并不是创建一个真实的对象，而是模拟这个对象，他用简单的when(mock.method(params)).thenRetrun(result)语句设置mock对象的行为，如下语句：
	```
	// 设置mock对象的行为 － 当调用其get方法获取第0个元素时，返回"first"
	Mockito.when(mockedList.get(0)).thenReturn("first");
	```
	在Mock对象的时候，创建一个proxy对象，保存被调用的方法名（get），以及调用时候传递的参数（0），然后在调用thenReturn方法时再把“first”保存起来，这样，就有了构建一个stub方法所需的所有信息，构建一个stub。当get方法被调用的时候，实际上调用的是之前保存的proxy对象的get方法，返回之前保存的数据。