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
*  This method will block unit
*  the bytes are actually written.
*  调用write写输出流的时候，会被阻塞直到所有字节写入完毕。
*/
OutputStream.write(byte b[]) throws IOException{}
```