### 同步阻塞
看两个JAVA同步I/O的API说明
```
这两个逼的读和写操作都是同步阻塞的。

/**
* This method blocks until
* 1. input data is available 有数据可读
* 2. the end of the stream is detected　可读数据已经读取完毕
* 3. an exception is thrown 异常,空指针或者IO异常
*/
InputStream.read(byte b[]) throws IOException{
    return read(b, 0, b.length);
}


/**
*  This method will block unitl
*  the bytes are actually written.
*  调用write写输出流的时候，会被阻塞直到所有字节写入完毕。
*/
OutputStream.write(byte b[]) throws IOException{}
```


### JDK NIO API

 Selector 多路复用器  
 监听事件,比如SelectionKey.OP_ACCEPT官方文档片段如下
```
 If the selector detects that the corresponding server-socket channel is ready to accept
 another connection, or has an error pending, then it will add
 <tt>OP_ACCEPT</tt> to the key's ready set and add the key to its
 selected-key set.

```

 NIO 跟 AIO的性能差距不是很大
 

### TCP 粘包/拆包问题
解决方案:
1. 消息定长，例如每个报文的大小固定长度200字节，如果不够，空位补空格。
2. 在包尾添加回车换行符或者其他自定义的符号进行分割，例如FTP协议(回车换行符 \n \r\n)
3. 将消息分为消息头，消息体，消息头中包涵消息总长度(或消息体长度，只要定义好头长度就好了)
4. 更复杂的应用层协议