create database caphepoly;
use caphepoly;
CREATE TABLE [dbo].[NGUOIDUNG](
	[MaND] [nvarchar](15) NOT NULL,
	[TenDN] [nvarchar](50) NOT NULL,
	[MatKhau] [nvarchar](50) NOT NULL,
	[PhanQuyen] [nvarchar](40) NOT NULL,
	[TrangThai] [bit] NOT NULL,
	primary key(TenDN)
)
CREATE TABLE [dbo].[NHANVIEN](
	[MaNV] [nvarchar](15) NOT NULL,
	[TenNV] [nvarchar](100) NOT NULL,
	[GioiTinh] [bit] NOT NULL,
	[SDT] [varchar](10) NOT NULL,
	[DiaChi] [nvarchar](200) NOT NULL,
	[ChucVu] [nvarchar](40) NOT NULL,
	[Email] [nvarchar](100) NOT NULL,
	[NgayVaoLam] [date] NOT NULL,
	primary key(MaNV)
)
CREATE TABLE [dbo].[CALAMVIEC](
	[MaCa] [nvarchar](10) NOT NULL,
	[TenCa] [nvarchar](20) NOT NULL,
	[GioBatDau] [time](7) NOT NULL,
	[GioKetThuc] [time](7) NOT NULL,
	[Luong] [float] NOT NULL,
	primary key (MaCa)
 )
 CREATE TABLE [dbo].[LUONGCHITIET](
	[MaLuongCT] [nvarchar](10) NOT NULL,
	[NgayLamViec] [date] NOT NULL,
	[MaCa] [nvarchar](10) NULL,
	[MaNV] [nvarchar](15) NULL,
	primary key (MaLuongCT),
	foreign key(MaCa) references CALAMVIEC(MaCa) ,
	foreign key(MaNV) references NhanVien(MaNV) 
 )


CREATE TABLE [dbo].[SANPHAM](
	[MaSP] [nvarchar](10) NOT NULL,
	[TenSP] [nvarchar](40) NOT NULL,
	[MaLoai] [nvarchar](10) NOT NULL,
	[SoLuong] [int] NOT NULL,
	[GiaNhap] [float] NOT NULL,
	[GiaBan] [float] NOT NULL,
	[NgayNhap] [date] NOT NULL,
	[MoTa] [nvarchar](100) NULL,
	[HinhAnh] [nvarchar](100) NULL,
	[TrangThai] [bit] NOT NULL,
 primary key (MaSP)
 );
 CREATE TABLE [dbo].[HOADON](
	[MaHD] [nvarchar](10) NOT NULL,
	[NgayTao] [date] NOT NULL,
	[TenDN] [nvarchar](50) NOT NULL,
	[TrangThai] [bit] NOT NULL,
	[Huy] [bit] NULL,
	[Ban] [int] NULL,
	[DangBan] [bit] NULL,
  primary key (MaHD),
  foreign key(TenDN) references NguoiDung(TenDN),
)
CREATE TABLE [dbo].[HOADONCT](
	[MaHD] [nvarchar](10) NOT NULL,
	[MaHDCT] [nvarchar](10) NOT NULL,
	[MaSP] [nvarchar](10) NOT NULL,
	[SoLuong] [int] NOT NULL,
	[GiamGia] [float] NOT NULL,
	[DonGia] [float] NOT NULL,
	primary key (MaHDCT),
    foreign key(MaHD) references HoaDon(MaHD),
	foreign key(MaSP) references SanPham(MaSP),

)
CREATE TABLE [dbo].[BAN](
	[MaBan] [int] NOT NULL,
	[TenBan] [nvarchar](20) NOT NULL,
	[TrangThai] [bit] NULL,
    primary key (MaBan)
	)
CREATE TABLE [dbo].[LOAISANPHAM](
	[MaLoai] [nvarchar](10) NOT NULL,
	[TenLoai] [nvarchar](30) NOT NULL,
	[MoTa] [nvarchar](100) NULL,
    primary key(MaLoai)
)


ALTER TABLE [dbo].[HOADON]  WITH CHECK ADD  CONSTRAINT [FK_HOADON_BAN] FOREIGN KEY([Ban])
REFERENCES [dbo].[BAN] ([MaBan])
ALTER TABLE [dbo].[HOADON] CHECK CONSTRAINT [FK_HOADON_BAN]
INSERT [dbo].[BAN] ([MaBan], [TenBan], [TrangThai]) VALUES (1, N'Bàn 1', 0)
INSERT [dbo].[BAN] ([MaBan], [TenBan], [TrangThai]) VALUES (2, N'Bàn 2', 0)
INSERT [dbo].[BAN] ([MaBan], [TenBan], [TrangThai]) VALUES (3, N'Bàn 3', 0)
INSERT [dbo].[BAN] ([MaBan], [TenBan], [TrangThai]) VALUES (4, N'Bàn 4', 0)
INSERT [dbo].[BAN] ([MaBan], [TenBan], [TrangThai]) VALUES (5, N'Bàn 5', 0)




INSERT [dbo].[LOAISANPHAM] ([MaLoai], [TenLoai], [MoTa]) VALUES (N'L001', N'Cà phê ', N'')
INSERT [dbo].[LOAISANPHAM] ([MaLoai], [TenLoai], [MoTa]) VALUES (N'L002', N'Nước ngọt', N'')


INSERT [dbo].[SANPHAM] ([MaSP], [TenSP], [MaLoai], [SoLuong], [GiaNhap], [GiaBan], [NgayNhap], [MoTa], [HinhAnh], [TrangThai]) VALUES (N'SP001', N'Cà phê đen đá', N'L001', 77, 10000, 12000, CAST(N'2021-12-11' AS Date), N'', N'', 1)
INSERT [dbo].[SANPHAM] ([MaSP], [TenSP], [MaLoai], [SoLuong], [GiaNhap], [GiaBan], [NgayNhap], [MoTa], [HinhAnh], [TrangThai]) VALUES (N'SP002', N'Trà xanh 0 độ', N'L002', 91, 7000, 8000, CAST(N'2021-08-12' AS Date), N'', N'', 1)
INSERT [dbo].[SANPHAM] ([MaSP], [TenSP], [MaLoai], [SoLuong], [GiaNhap], [GiaBan], [NgayNhap], [MoTa], [HinhAnh], [TrangThai]) VALUES (N'SP003', N'Trà đào', N'L002', 14, 12000, 13000, CAST(N'2021-12-15' AS Date), N'', N'', 1)
INSERT [dbo].[SANPHAM] ([MaSP], [TenSP], [MaLoai], [SoLuong], [GiaNhap], [GiaBan], [NgayNhap], [MoTa], [HinhAnh], [TrangThai]) VALUES (N'SP004', N'Cà phê sữa đá', N'L001', 2, 6000, 12000, CAST(N'2021-12-15' AS Date), N'', N'', 1)
INSERT [dbo].[SANPHAM] ([MaSP], [TenSP], [MaLoai], [SoLuong], [GiaNhap], [GiaBan], [NgayNhap], [MoTa], [HinhAnh], [TrangThai]) VALUES (N'SP005', N'Coca cola', N'L002', 100, 12000, 15000, CAST(N'2021-12-15' AS Date), N'', N'', 1)
INSERT [dbo].[SANPHAM] ([MaSP], [TenSP], [MaLoai], [SoLuong], [GiaNhap], [GiaBan], [NgayNhap], [MoTa], [HinhAnh], [TrangThai]) VALUES (N'SP006', N'Pepsi', N'L002', 1, 13000, 14000, CAST(N'2021-12-18' AS Date), N'', N'', 1)


INSERT [dbo].[CALAMVIEC] ([MaCa], [TenCa], [GioBatDau], [GioKetThuc], [Luong]) VALUES (N'1', N'ca 1', CAST(N'07:30:00' AS Time), CAST(N'08:45:00' AS Time), 60000)
INSERT [dbo].[CALAMVIEC] ([MaCa], [TenCa], [GioBatDau], [GioKetThuc], [Luong]) VALUES (N'2', N'ca 2', CAST(N'07:00:00' AS Time), CAST(N'18:30:00' AS Time), 25000)



INSERT [dbo].[HOADON] ([MaHD], [NgayTao], [TenDN], [TrangThai], [Huy], [Ban], [DangBan]) VALUES (N'HD002', CAST(N'2021-12-17' AS Date), N'vvhh123', 1, 0, 3, 0)


create procedure [dbo].[sp_HoaDonChiTiet] @MaHD nvarchar(10)
as
begin
select a.MaSP,b.TenSP,b.GiaBan,a.SoLuong,a.GiamGia,a.DonGia
from HOADONCT a inner join SANPHAM b
on a.MaSP = b.MaSP
where a.MaHD = @MaHD
end
GO