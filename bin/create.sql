-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.5.61-log - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Export de la structure de la base pour basevelo_test
DROP DATABASE IF EXISTS `basevelo_test`;
CREATE DATABASE IF NOT EXISTS `basevelo_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `basevelo_test`;

-- Export de la structure de la table basevelo_test. cycliste
DROP TABLE IF EXISTS `cycliste`;
CREATE TABLE IF NOT EXISTS `cycliste` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `velo` varchar(50) NOT NULL,
  `equipe_id` int(11) NOT NULL,
  `nombre_velos` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_cycliste_equipe` (`equipe_id`),
  CONSTRAINT `FK_cycliste_equipe` FOREIGN KEY (`equipe_id`) REFERENCES `equipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Export de données de la table basevelo_test.cycliste : ~0 rows (environ)
/*!40000 ALTER TABLE `cycliste` DISABLE KEYS */;
INSERT INTO `cycliste` (`id`, `name`, `equipe_id`, `nombre_velos`) VALUES
	(1, 'Marcellin', 3, 3),
	(2, 'Espagnive', 3, 5),
	(3, 'Paralo', 2, 2),
	(4, 'Pesdori', 2, 1),
	(5, 'Cremone', 1, 1);
/*!40000 ALTER TABLE `cycliste` ENABLE KEYS */;

-- Export de la structure de la table basevelo_test. equipe
DROP TABLE IF EXISTS `equipe`;
CREATE TABLE IF NOT EXISTS `equipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `budget` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Export de données de la table basevelo_test.equipe : ~0 rows (environ)
/*!40000 ALTER TABLE `equipe` DISABLE KEYS */;
INSERT INTO `equipe` (`id`, `name`, `budget`) VALUES
	(1, 'Equipe Total', 500000),
	(2, 'Equipe Carrefour', 600000),
	(3, 'Equipe Axa', 700);
/*!40000 ALTER TABLE `equipe` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
