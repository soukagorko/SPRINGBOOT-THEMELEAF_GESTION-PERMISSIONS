-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 01 oct. 2024 à 15:31
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bd_springboot_thymeleaf_gestion-permission`
--

-- --------------------------------------------------------

--
-- Structure de la table `demandes`
--

CREATE TABLE `demandes` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `destinataire_demande` varchar(255) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `duree_permission` int(11) DEFAULT NULL,
  `lieu` varchar(50) DEFAULT NULL,
  `motif` varchar(50) NOT NULL,
  `objet` varchar(100) DEFAULT NULL,
  `statut` int(11) DEFAULT 0,
  `personnel_id` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `date_demande` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `demandes`
--

INSERT INTO `demandes` (`id`, `created_at`, `date_debut`, `date_fin`, `destinataire_demande`, `destination`, `duree_permission`, `lieu`, `motif`, `objet`, `statut`, `personnel_id`, `service_id`, `user_id`, `date_demande`) VALUES
(1, NULL, '2024-07-15', '2024-07-17', 'Chef de Brigade Speciale', 'Taiba Ndiaye (Thies)', 48, 'Diass, le', 'Fete Tamkharite', 'Demande d\'une autorisation d\'absence', 1, 4, 2, 1, '2024-07-14 21:06:23.000000'),
(2, NULL, '2024-07-16', '2024-07-19', 'Commissaire Special de l\'AIBD                    ', 'Dakar', 72, 'Diass, le', 'Fete Tamkharite', 'Demande d\'une autorisation d\'absence', 0, 1, 6, 3, '2024-07-15 21:07:04.000000'),
(3, NULL, '2024-07-16', '2024-07-18', 'Commissariat Special de l\'AIBD', 'Thies', 48, 'Diass, le', 'Cas familial', 'Demande d\'une autorisation d\'absence', 0, 2, 1, 3, '2024-07-15 21:07:10.000000'),
(4, NULL, '2024-07-17', '2024-07-18', 'Commandant troisieme brigade                    ', 'Dakar', 24, 'Diass, le', 'Fete Tamkharite', 'Demande d\'une autorisation d\'absence', 1, 7, 5, 4, '2024-07-16 21:24:07.000000'),
(5, NULL, '2024-07-17', '2024-07-21', 'Commissaire Special de l\'AIBD                    ', 'Thies', 96, 'Diass, le', 'Stage', 'Demande d\'une autorisation d\'absence', 0, 13, 6, 1, '2024-07-16 21:24:01.000000'),
(6, NULL, '2024-07-22', '2024-07-23', 'Commissariat Special de l\'AIBD', 'Dakar', 24, 'Diass, le', 'Cas familial', 'Demande d\'une autorisation d\'absence', 0, 12, 1, 1, '2024-07-21 21:23:57.000000'),
(7, NULL, '2024-07-23', '2024-07-25', 'Chef de Brigade Speciale', 'Dakar', 48, 'Diass, le', 'Stage', 'Demande d\'une autorisation d\'absence', 0, 11, 2, 1, '2024-07-22 21:23:39.000000'),
(8, NULL, '2024-07-24', '2024-07-28', 'Commandant premiere brigade d\'arraisonnement                   ', 'Thies', 96, 'Diass, le', 'Stage', 'Demande d\'une autorisation d\'absence', 2, 8, 3, 1, '2024-07-23 21:23:29.000000'),
(9, NULL, '2024-07-29', '2024-07-31', 'Commandant deuxieme brigade d\'arraisonnement', 'Nioro du Rip', 48, 'Diass, le', 'Cas familial', 'Demande d\'une autorisation d\'absence', 2, 6, 4, 1, '2024-07-28 21:23:19.000000'),
(10, NULL, '2024-07-24', '2024-07-26', 'Commandant troisieme brigade                    ', 'Dakar', 48, 'Diass, le', 'Cas familial', 'Demande d\'une autorisation d\'absence', 0, 9, 5, 1, '2024-07-23 21:21:14.000000'),
(11, NULL, '2024-07-24', '2024-07-28', 'Commandant deuxieme brigade d\'arraisonnement', 'Ziguinchor', 96, 'Diass, le', 'Cas familial', 'Demande d\'une autorisation d\'absence', 2, 21, 4, 5, '2024-07-23 21:15:29.000000'),
(12, NULL, '2024-08-07', '2024-08-09', 'Commandant premiere brigade d\'arraisonnement                   ', 'Dakar', 48, 'Diass, le', 'Cas familiale', 'Demande d\'une autorisation d\'absence', 1, 14, 3, 6, '2024-08-06 21:19:30.000000'),
(13, NULL, '2024-08-08', '2024-08-10', 'Commandant premiere brigade d\'arraisonnement                   ', 'Ziguinchor', 48, 'Diass, le', 'RV médical', 'Demande d\'une autorisation d\'absence', 0, 16, 3, 6, '2024-08-07 21:14:39.000000'),
(14, NULL, '2024-08-08', '2024-08-09', 'Commandant deuxieme brigade d\'arraisonnement', 'Dakar', 24, 'Diass, le', 'Cas familial', 'Demande d\'une autorisation d\'absence', 1, 19, 4, 5, '2024-08-07 21:14:33.000000'),
(15, NULL, '2024-08-12', '2024-08-15', 'Commandant deuxieme brigade d\'arraisonnement', 'Dakar', 72, 'Diass, le', 'RV médical', 'Demande d\'une autorisation d\'absence', 0, 20, 4, 5, '2024-08-11 21:14:20.000000'),
(16, NULL, '2024-08-09', '2024-08-10', 'Commandant deuxieme brigade d\'arraisonnement', 'Thies', 24, 'Diass, le', 'Stage', 'Demande d\'une autorisation d\'absence', 1, 21, 4, 5, '2024-08-08 21:14:06.000000'),
(17, NULL, '2024-08-22', '2024-08-25', 'Commandant deuxieme brigade d\'arraisonnement', 'Touba', 72, 'Diass, le', 'Magal', 'Demande d\'une autorisation d\'absence', 1, 19, 4, 5, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `departement`
--

CREATE TABLE `departement` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `departement_seq`
--

CREATE TABLE `departement_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `departement_seq`
--

INSERT INTO `departement_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Structure de la table `donnees`
--

CREATE TABLE `donnees` (
  `id` bigint(20) NOT NULL,
  `statut` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

CREATE TABLE `employe` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `statut` int(11) NOT NULL,
  `departement_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `employe_seq`
--

CREATE TABLE `employe_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `employe_seq`
--

INSERT INTO `employe_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Structure de la table `permissions`
--

CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL,
  `date_permission` datetime(6) DEFAULT NULL,
  `statut` int(11) DEFAULT 0,
  `demande_id` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `permissions`
--

INSERT INTO `permissions` (`id`, `date_permission`, `statut`, `demande_id`, `service_id`, `user_id`) VALUES
(1, '2024-07-20 13:05:39.000000', 1, 1, 2, 1),
(2, '2024-07-20 13:06:08.000000', 1, 4, 5, 1),
(3, '2024-07-25 23:52:20.000000', 1, 11, 4, 11),
(4, '2024-08-06 19:16:54.000000', 1, 12, 3, 9),
(5, '2024-08-06 19:19:31.000000', 2, 8, 3, 9),
(6, '2024-08-06 19:21:20.000000', 2, 9, 4, 11),
(7, '2024-08-06 19:21:26.000000', 1, 14, 4, 11),
(8, '2024-08-21 16:33:33.000000', 1, 16, 4, 11),
(9, '2024-08-21 20:38:33.000000', 1, 17, 4, 11);

-- --------------------------------------------------------

--
-- Structure de la table `personnels`
--

CREATE TABLE `personnels` (
  `id` bigint(20) NOT NULL,
  `contact` varchar(30) DEFAULT NULL,
  `date_arrivee` date DEFAULT NULL,
  `grade` varchar(50) DEFAULT NULL,
  `matricule` varchar(20) DEFAULT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `statut` varchar(10) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `personnels`
--

INSERT INTO `personnels` (`id`, `contact`, `date_arrivee`, `grade`, `matricule`, `nom`, `prenom`, `statut`, `service_id`) VALUES
(1, '77 973 73 33', '2024-07-08', 'Adjudant de Police', '716 341/L', 'DIALLO', 'Abdoulaye', '1', 6),
(2, 'NON RENSEIGNE', NULL, 'Agent de Police', 'NON RENSEIGNE', 'DIALLO', 'Moustapha', '1', 1),
(3, '77 897 66 55', NULL, 'Agent de Police', 'NON RENSEIGNE', 'KA', 'Mody', '1', 3),
(4, '70 998 76 34', NULL, 'Agent de Police', '567 908/P', 'NDIAYE', 'Mamour', '1', 2),
(5, '77 529 15 54', NULL, 'Lieutenant de Police', 'NON RENSEIGNE', 'FAYE', 'Charles Diogoul', '1', 2),
(6, '70 998 76 34', '2022-04-02', 'Agent de Police', 'NON RENSEIGNE', 'BITEYE', 'Mamadou', '1', 4),
(7, '77 897 66 55', '2022-01-25', 'Agent de Police', 'NON RENSEIGNE', 'MANE', 'Amadou Oury', '1', 5),
(8, 'NON RENSEIGNE', '2024-07-02', 'Lieutenant de Police', 'NON RENSEIGNE', 'KAIRE', 'Mamadou', '1', 3),
(9, 'NON RENSEIGNE', '2024-07-18', 'Adjudant de Police', 'NON RENSEIGNE', 'NDIAYE', 'Alioune Badara', '1', 5),
(10, 'NON RENSEIGNE', '2024-07-10', 'Brigadier Chef des Agents de Police', 'NON RENSEIGNE', 'DIAGNE', 'Mamadou', '1', 2),
(11, 'NON RENSEIGNE', '2024-07-17', 'Agent de Police', 'NON RENSEIGNE', 'WADE', 'Mouhamadou Lamine', '1', 2),
(12, 'NON RENSEIGNE', '2024-07-10', 'Lieutenant de Police', 'NON RENSEIGNE', 'FAYE', 'Ngor', '1', 1),
(13, 'NON RENSEIGNE', '2024-07-09', 'Adjudant de Police', 'NON RENSEIGNE', 'NDONGO', 'Seydina Ibrahima', '1', 6),
(14, 'NON RENSEIGNE', '2024-07-15', 'Brigadier des agents de Police', 'NON RENSEIGNE', 'DIALLO', 'Omar', '1', 3),
(15, 'NON RENSEIGNE', '2024-07-08', 'Brigadier Chef des Agents de Police', 'NON RENSEIGNE', 'Ndangane', 'El H Momar', '1', 3),
(16, 'NON RENSEIGNE', '2024-07-02', 'Agent de Police', 'NON RENSEIGNE', 'SANE', 'Yankhouba', '1', 3),
(17, 'NON RENSEIGNE', '2024-06-26', 'Agent de Police', 'NON RENSEIGNE', 'NDIAYE', 'Bouba', '1', 3),
(18, 'NON RENSEIGNE', '2024-07-10', 'Agent de Police', 'NON RENSEIGNE', 'BA', 'Kalidou', '1', 4),
(19, 'NON RENSEIGNE', '2024-06-25', 'Agent de Police', 'NON RENSEIGNE', 'FATY', 'Babou Daffe', '1', 4),
(20, 'NON RENSEIGNE', '2024-06-24', 'Agent de Police', 'NON RENSEIGNE', 'MBOUP', 'Fatou', '1', 4),
(21, 'NON RENSEIGNE', '2024-07-02', 'Adjudant de Police', 'NON RENSEIGNE', 'FAYE', 'Jerome Massaba', '1', 4);

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `desc_role` varchar(50) DEFAULT NULL,
  `nom_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id`, `desc_role`, `nom_role`) VALUES
(1, 'Administrateur', 'ADMIN'),
(2, 'Utilisateur', 'USER'),
(3, 'Secretariat particulier du CSA                    ', 'SPCSA'),
(4, 'Secretaire de brigade                    ', 'SECRETAIRE-BRIGADE'),
(5, 'Role 1                    ', 'ROLE 1'),
(6, 'Chef de service                    ', 'ChefDeService'),
(7, 'Chef de brigade                    ', 'ChefDeBrigade');

-- --------------------------------------------------------

--
-- Structure de la table `services`
--

CREATE TABLE `services` (
  `id` bigint(20) NOT NULL,
  `contact_service` varchar(30) DEFAULT NULL,
  `desc_service` varchar(100) NOT NULL,
  `fonction_chef_service` varchar(100) DEFAULT NULL,
  `grade_chef_service` varchar(50) DEFAULT NULL,
  `nom_chef_service` varchar(50) DEFAULT NULL,
  `nom_service` varchar(50) DEFAULT NULL,
  `sigle_service` varchar(20) NOT NULL,
  `statut_chef_service` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `services`
--

INSERT INTO `services` (`id`, `contact_service`, `desc_service`, `fonction_chef_service`, `grade_chef_service`, `nom_chef_service`, `nom_service`, `sigle_service`, `statut_chef_service`) VALUES
(1, '77 529 00 37', 'Secretariat Particulier du Commissariat Special de l\'AIBD ', 'Commissariat Special de l\'AIBD', 'Commissaire Divisionnaire', 'Abdou LEYE', 'Secretariat Particulier du CSA', 'SP CSA', 'Monsieur/Madame'),
(2, '77 529 15 54', 'Brigade Speciale du Commissariat Special de l\'AIBD                    ', 'Chef de Brigade Speciale', 'Lieutenant de Police', 'Charles Diogoul FAYE', 'Brigade Speciale', 'BS', 'Monsieur/Madame'),
(3, '77 529 02 77', 'Premiere brigade d\'arraisonnement du Commissariat SpÃƒÂ©cial de l\'AIBD                    ', 'Commandant premiere brigade d\'arraisonnement                   ', 'Lieutenant de Police', 'Mamadou KAIRE', '1ere Brigade', 'B1', 'Monsieur/Madame'),
(4, '77 529 02 78', 'Deuxieme brigade d\'arraisonnement du Commissariat SpÃƒÆ’Ã‚Â©cial de l\'AIBD', 'Commandant deuxieme brigade d\'arraisonnement', 'Lieutenant de Police', 'Mamadou Lamine DABA', '2eme Brigade', 'B2', 'Monsieur/Madame'),
(5, '77 529 02 79', 'Troisieme brigade d\'arraisonnement du CSA                    ', 'Commandant troisieme brigade                    ', 'Lieutenant de Police', 'Lt BALDE', '3eme Brigade', 'B3', 'Monsieur/Madame'),
(6, 'NON RENSEIGNE', 'Centre de monitoring et de profiling du CSA                    ', 'Commissaire Special de l\'AIBD                    ', 'Commissaire Divisionnaire', 'Abdou LEYE', 'Centre de Monitoring et de Profiling', 'CMP/AIBD', 'Monsieur/Madame');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `contact`, `nom`, `password`, `prenom`, `username`, `role_id`, `service_id`) VALUES
(1, '77 973 73 33', 'DIALLO', '$2a$10$jQTF2zwFUBQaZyoAmB9A.u8AzUdoLECrIU1R5e5D1Vwcv3szctYpy', 'Abdoulaye', 'admin', 1, 6),
(3, '77 440 89 78', 'MANE', '$2a$10$YgbxiRIR/lRXREzU/yuBYeQDbwHUGZsgGw/BDsToA1rXc6uJo2xli', 'Malang', 'malang', 3, 1),
(4, 'NON RENSEIGNE', 'THIOUF', '$2a$10$EO8c8JaYHfm6sDjNgIYiiuMSoMrMwylNXBkXqNdZMFUbUMTKTEm5W', 'Cheikh Tidiane', 'ctthiouf', 3, 1),
(5, 'NON RENSEIGNE', 'DIA', '$2a$10$RXJEuFDG34AyYEWW.Z73zethdj8py0Q0XUqsWcH5Wl4q.PpMsOzTq', 'Aliou', 'adia', 4, 4),
(6, '77 440 89 77', 'BEYE', '$2a$10$K20CHHinmJ6E9XkyQ5gZ5.6f7QhNeHym0x0tMn7dBLbdY2yrmJi4.', 'Alkhassimou', 'abeye', 4, 3),
(7, '76 012 13 13', 'KOTO', '$2a$10$Sj1ddJrk3PZdFKMi0VyNYuy8OuaRlHgo5SKUmngN1YU141rQoUJ4K', 'Kaba', 'user1', 2, 6),
(8, '77 529 00 37', 'LEYE', '$2a$10$l1vn2c26Zl/MppYW/6V62OMLz8Bc4dck9HxYQIEV.C3WZH6Y0k.qq', 'Abdou', 'aleye', 6, 1),
(9, '77 440 89 78', 'KAIRE', '$2a$10$iEv/r6Oh1HbF6Tsm4T6.fu8YpZmIdOVhK.qX31wxUp3tIdW1C0hpa', 'Mamadou', 'akaire', 7, 3),
(10, '77 440 89 79', 'BALDE', '$2a$10$TPP7pv9GJZcMNZm/K5P3VO4Nr2aqjNViJUcEYjuurIuhYzrgyWHfa', 'Lt', 'balde', 7, 5),
(11, '77 529 00 78', 'DABO', '$2a$10$wCHKc6Q5TbmeW9fuciyDo.j6QHa0b9CHCaqAMia9BdiJGCsMJYNJK', 'Lt', 'dabo', 7, 4);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `demandes`
--
ALTER TABLE `demandes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK53468hpg7f2r31uu0cjbnfhyj` (`personnel_id`),
  ADD KEY `FK8dh62tlfihkf92kq0at0fkj07` (`service_id`),
  ADD KEY `FK5q0nr046jlvo9r8p0qxs95jr9` (`user_id`);

--
-- Index pour la table `departement`
--
ALTER TABLE `departement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `donnees`
--
ALTER TABLE `donnees`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `employe`
--
ALTER TABLE `employe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2omvhd1hgk3im7sg6jqunf1lg` (`departement_id`);

--
-- Index pour la table `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqul4a57t5y08dyhu9r5kmgbq9` (`demande_id`),
  ADD KEY `FKkv254el9pd6s44j778esbbhvy` (`service_id`),
  ADD KEY `FK2vnmjh5vw8m96emb2x1web77p` (`user_id`);

--
-- Index pour la table `personnels`
--
ALTER TABLE `personnels`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK22b1arskt8jydoy3640qmfbdn` (`service_id`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  ADD KEY `FKg28emhyfqgy7bu8nv5ol805wt` (`service_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `demandes`
--
ALTER TABLE `demandes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `permissions`
--
ALTER TABLE `permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `personnels`
--
ALTER TABLE `personnels`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `services`
--
ALTER TABLE `services`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `demandes`
--
ALTER TABLE `demandes`
  ADD CONSTRAINT `FK53468hpg7f2r31uu0cjbnfhyj` FOREIGN KEY (`personnel_id`) REFERENCES `personnels` (`id`),
  ADD CONSTRAINT `FK5q0nr046jlvo9r8p0qxs95jr9` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK8dh62tlfihkf92kq0at0fkj07` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`);

--
-- Contraintes pour la table `employe`
--
ALTER TABLE `employe`
  ADD CONSTRAINT `FK2omvhd1hgk3im7sg6jqunf1lg` FOREIGN KEY (`departement_id`) REFERENCES `departement` (`id`);

--
-- Contraintes pour la table `permissions`
--
ALTER TABLE `permissions`
  ADD CONSTRAINT `FK2vnmjh5vw8m96emb2x1web77p` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKkv254el9pd6s44j778esbbhvy` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  ADD CONSTRAINT `FKqul4a57t5y08dyhu9r5kmgbq9` FOREIGN KEY (`demande_id`) REFERENCES `demandes` (`id`);

--
-- Contraintes pour la table `personnels`
--
ALTER TABLE `personnels`
  ADD CONSTRAINT `FK22b1arskt8jydoy3640qmfbdn` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`);

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKg28emhyfqgy7bu8nv5ol805wt` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
