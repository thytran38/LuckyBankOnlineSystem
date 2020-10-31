# Hướng dẫn thực hiện dự án Lucky Online Banking System

# 
### Clone dự án về máy PC của bạn

Để tạo bản sao làm việc cục bộ của kho lưu trữ từ xa hiện có, hãy sử dụng git clone để sao chép và tải kho lưu trữ xuống máy tính. Sao chép tương đương với git init khi làm việc với kho lưu trữ từ xa. Git sẽ tạo một thư mục cục bộ với tất cả các tệp và lịch sử kho lưu trữ.
##### Trên Command Line Tool
###### git clone https://github.com/thytran38/LuckyBanks.git


# 
### Thêm một file vào staging area 
Staging area là nơi chuẩn bị cho quá trình commit. Về cơ bản các hệ thống quản lý phiên bản (Version Control System) có hai nơi lưu trữ dữ liệu: một là thư mục mà bạn đang làm việc trên máy tính, và hai là kho chứa mã nguồn (còn gọi là repository) được lưu trữ từ xa, ví dụ Github.

Cho nên staging area là nơi lưu trữ trung gian, mỗi tập tin nào muốn được commit thì nó phải nằm ở trong khu vực staging area. Và một tập tin khi nó nằm trong khu vực này sẽ có trạng thái Stagged.

![](https://thachpham.com/wp-content/uploads/2015/04/git-staging-area.png?raw=true)

Để đưa một file vào Staging Area, ta dùng lệnh:

Git add tên-file 






