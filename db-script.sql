--
-- Baza danych: `bookingfx`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Faktury`
--

CREATE TABLE `Faktury` (
  `NR_FAKTURA` int(11) NOT NULL,
  `DATA_WYSTAWIENIA` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `KWOTA_FAKTURY` decimal(15,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Kursy`
--

CREATE TABLE `Kursy` (
  `WALUTA` char(3) NOT NULL,
  `KURS` decimal(8,4) NOT NULL,
  `DATA_KURSU` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Kursy`
--

INSERT INTO `Kursy` (`WALUTA`, `KURS`, `DATA_KURSU`) VALUES
('EUR', '4.0000', '2019-01-15');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Pokoje`
--

CREATE TABLE `Pokoje` (
  `NR_POKOJ` int(11) NOT NULL,
  `LICZBA_OSOB` int(11) NOT NULL,
  `CENA` decimal(15,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Pokoje`
--

INSERT INTO `Pokoje` (`NR_POKOJ`, `LICZBA_OSOB`, `CENA`) VALUES
(121, 4, '400.00'),
(120, 4, '400.00'),
(119, 2, '100.00'),
(118, 2, '100.00'),
(117, 2, '125.00'),
(116, 2, '150.00'),
(115, 2, '150.00'),
(114, 2, '125.00'),
(113, 2, '100.00'),
(112, 2, '100.00'),
(111, 1, '200.00'),
(110, 1, '200.00'),
(109, 1, '225.00'),
(108, 1, '225.00'),
(107, 1, '250.00'),
(106, 1, '250.00'),
(105, 1, '250.00'),
(104, 1, '225.00'),
(103, 1, '225.00'),
(102, 1, '200.00'),
(101, 1, '200.00'),

(201, 1, '200.00'),
(202, 1, '200.00'),
(203, 1, '225.00'),
(204, 1, '225.00'),
(205, 1, '250.00'),
(206, 1, '250.00'),
(207, 1, '250.00'),
(208, 1, '225.00'),
(209, 1, '225.00'),
(210, 1, '200.00'),
(211, 1, '200.00'),
(212, 2, '100.00'),
(213, 2, '100.00'),
(214, 2, '125.00'),
(215, 2, '150.00'),
(216, 2, '150.00'),
(217, 2, '125.00'),
(218, 2, '100.00'),
(219, 2, '100.00'),
(220, 4, '400.00'),
(221, 4, '400.00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Rezerwacje`
--

CREATE TABLE `Rezerwacje` (
  `ID_REZERWACJA` int(11) NOT NULL,
  `NR_FAKTURA` int(11) NOT NULL,
  `NR_POKOJ` int(11) NOT NULL,
  `LOGIN` varchar(20) NOT NULL,
  `DATA_OD` date NOT NULL,
  `DATA_DO` date NOT NULL,
  `WALUTA` varchar(3) NOT NULL,
  `KWOTA_REZERWACJI` decimal(15,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Uzytkownicy`
--

CREATE TABLE `Uzytkownicy` (
  `LOGIN` varchar(20) NOT NULL,
  `IMIE` varchar(30) NOT NULL,
  `NAZWISKO` varchar(30) NOT NULL,
  `NR_KARTY_KRED` decimal(16,0) NOT NULL,
  `PESEL` decimal(11,0) NOT NULL,
  `NR_TEL` decimal(11,0) DEFAULT '0',
  `HASLO` varchar(64) NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  `TOKEN` varbinary(16) NOT NULL,
  `UPRAWNIENIA` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `Faktury`
--
ALTER TABLE `Faktury`
  ADD PRIMARY KEY (`NR_FAKTURA`);

--
-- Indexes for table `Kursy`
--
ALTER TABLE `Kursy`
  ADD PRIMARY KEY (`WALUTA`);

--
-- Indexes for table `Pokoje`
--
ALTER TABLE `Pokoje`
  ADD PRIMARY KEY (`NR_POKOJ`);

--
-- Indexes for table `Rezerwacje`
--
ALTER TABLE `Rezerwacje`
  ADD PRIMARY KEY (`ID_REZERWACJA`),
  ADD KEY `FK_Faktura_Rezerwacja` (`NR_FAKTURA`),
  ADD KEY `FK_Klient_Rezerwacja` (`LOGIN`),
  ADD KEY `FK_Pokoj_Rezerwacja` (`NR_POKOJ`);

--
-- Indexes for table `Uzytkownicy`
--
ALTER TABLE `Uzytkownicy`
  ADD PRIMARY KEY (`LOGIN`),
  ADD UNIQUE KEY `Email_UNIQUE` (`EMAIL`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `Faktury`
--
ALTER TABLE `Faktury`
  MODIFY `NR_FAKTURA` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT dla tabeli `Rezerwacje`
--
ALTER TABLE `Rezerwacje`
  MODIFY `ID_REZERWACJA` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
