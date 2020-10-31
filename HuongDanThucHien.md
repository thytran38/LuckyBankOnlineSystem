# Hướng dẫn thực hiện dự án Lucky Online Banking System
 
### Clone dự án về máy PC của bạn

Để tạo bản sao làm việc cục bộ của kho lưu trữ từ xa hiện có, hãy sử dụng git clone để sao chép và tải kho lưu trữ xuống máy tính. Sao chép tương đương với git init khi làm việc với kho lưu trữ từ xa. Git sẽ tạo một thư mục cục bộ với tất cả các tệp và lịch sử kho lưu trữ.
##### Trên Command Line Tool
###### git clone https://github.com/thytran38/LuckyBanks.git



### Thêm một file vào staging area 
Staging area là nơi chuẩn bị cho quá trình commit. Về cơ bản các hệ thống quản lý phiên bản (Version Control System) có hai nơi lưu trữ dữ liệu: một là thư mục mà bạn đang làm việc trên máy tính, và hai là kho chứa mã nguồn (còn gọi là repository) được lưu trữ từ xa, ví dụ Github.

Cho nên staging area là nơi lưu trữ trung gian, mỗi tập tin nào muốn được commit thì nó phải nằm ở trong khu vực staging area. Và một tập tin khi nó nằm trong khu vực này sẽ có trạng thái Stagged.

![](https://thachpham.com/wp-content/uploads/2015/04/git-staging-area.png?raw=true)

Để đưa một file vào Staging Area, ta dùng lệnh:

##### Trên Command Line Tool
###### git add tên-file 



### Thực hiện Commit 

Ở lệnh trên, khi đưa một file vào trạng thái stagged, file đấy vẫn chưa nằm trên repository local của ta. 

Để commit một file, ta dùng lệnh:

##### Trên Command Line Tool
###### git commit -m "Lời nhắn (có thể có hoặc ko)"



### Thực hiện lệnh Push 

Sau khi một file đã được commit thành công, nghĩa là nó đã ở local repository của ta tuy nhiên remote repository (ví dụ như Github repo) thì chưa. Dùng lệnh git push o

Để commit một file, ta dùng lệnh:

##### Trên Command Line Tool
###### git commit -m "Lời nhắn (có thể có hoặc ko)"

<img align="center" width="100" height="100" src="https://i.stack.imgur.com/MgaV9.png">

### Merge conflict là gì và tại sao lại gây đau đầu nhiều như vậy?

Xung đột (conflict) xảy ra khi 2 (hoặc hơn) người cùng edit một file, hay dev A đang edit thì dev B xoá file đó. Lúc đó Git sẽ không biết điều nào là đúng nên sẽ đánh đấu (mark the file as being conflicted) và tạm dừng lại quá trình merge. 

Có 2 dạng merge conflict:

* Git không thể bắt đầu merge

* Git merge thất bại trong quá trình


Tổng quan về trạng thái của git và các lệnh tương quan

![](https://i.stack.imgur.com/MgaV9.png?raw=true)




