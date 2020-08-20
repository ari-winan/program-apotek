-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 10 Jun 2020 pada 07.09
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apocash`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `bayar`
--

CREATE TABLE `bayar` (
  `total` int(20) NOT NULL,
  `bayar` int(20) NOT NULL,
  `kembali` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `bayar`
--

INSERT INTO `bayar` (`total`, `bayar`, `kembali`) VALUES
(28000, 30000, 2000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `cetak_bon`
--

CREATE TABLE `cetak_bon` (
  `kode_transaksi` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `kode_obat2` varchar(20) NOT NULL,
  `nama_obat2` varchar(20) NOT NULL,
  `harga_obat` int(20) NOT NULL,
  `jumlah` int(20) NOT NULL,
  `subtotal` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `cetak_bon`
--

INSERT INTO `cetak_bon` (`kode_transaksi`, `tanggal`, `kode_obat2`, `nama_obat2`, `harga_obat`, `jumlah`, `subtotal`) VALUES
('KT022', '2020-04-25', 'KO001', 'AntiBiotik', 7000, 4, 28000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `obat`
--

CREATE TABLE `obat` (
  `kode_obat` varchar(20) NOT NULL,
  `nama_obat` varchar(20) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `stok` int(10) NOT NULL,
  `harga_kontrak` int(10) NOT NULL,
  `harga_jual` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `obat`
--

INSERT INTO `obat` (`kode_obat`, `nama_obat`, `satuan`, `stok`, `harga_kontrak`, `harga_jual`) VALUES
('KO001', 'AntiBiotik', 'Kaplet', 250, 6000, 7000),
('KO002', 'Paracetamol', 'Kaplet', 288, 8000, 8500),
('KO003', 'Panadol', 'Kaplet', 500, 12000, 13000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `kode_transaksi` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `kode_obat2` varchar(20) NOT NULL,
  `nama_obat2` varchar(20) NOT NULL,
  `harga_obat` int(20) NOT NULL,
  `jumlah` int(20) NOT NULL,
  `subtotal` int(20) NOT NULL,
  `total` int(20) NOT NULL,
  `bayar` int(20) NOT NULL,
  `kembali` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`kode_transaksi`, `tanggal`, `kode_obat2`, `nama_obat2`, `harga_obat`, `jumlah`, `subtotal`, `total`, `bayar`, `kembali`) VALUES
('KT001', '2020-04-03', 'KO001', 'Parasetamol', 7500, 5, 37500, 37500, 40000, 2500),
('KT002', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 5, 35000, 35000, 35000, 0),
('KT003', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 7, 49000, 49000, 50000, 1000),
('KT004', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 3, 21000, 21000, 21000, 0),
('KT005', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 4, 28000, 28000, 30000, 2000),
('KT006', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 105000, 105000, 0),
('KT006', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 9, 63000, 105000, 105000, 0),
('KT007', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 98000, 100000, 2000),
('KT007', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 8, 56000, 98000, 100000, 2000),
('KT008', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 105000, 120000, 15000),
('KT008', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 9, 63000, 105000, 120000, 15000),
('KT009', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 77000, 80000, 3000),
('KT009', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 5, 35000, 77000, 80000, 3000),
('KT009', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 77000, 80000, 3000),
('KT009', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 5, 35000, 77000, 80000, 3000),
('KT010', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 42000, 45000, 3000),
('KT011', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 42000, 45000, 3000),
('KT012', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 42000, 45000, 3000),
('KT013', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 7, 49000, 49000, 50000, 1000),
('KT014', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 7, 49000, 49000, 50000, 1000),
('KT015', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 2, 14000, 14000, 15000, 1000),
('KT016', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 6, 42000, 42000, 50000, 8000),
('KT017', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 5, 35000, 35000, 40000, 5000),
('KT018', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 5, 35000, 35000, 40000, 5000),
('KT019', '2020-04-06', 'KO001', 'AntiBiotik', 7000, 7, 49000, 49000, 50000, 1000),
('KT020', '2020-04-23', 'KO001', 'AntiBiotik', 7000, 4, 28000, 28000, 30000, 2000),
('KT021', '2020-04-23', 'KO002', 'Paracetamol', 8500, 6, 51000, 51000, 60000, 9000),
('KT022', '2020-04-25', 'KO001', 'AntiBiotik', 7000, 4, 28000, 28000, 30000, 2000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`kode_obat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
