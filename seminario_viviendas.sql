-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 24-03-2022 a las 21:36:14
-- Versión del servidor: 5.7.31
-- Versión de PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `seminario_viviendas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `beneficio`
--

DROP TABLE IF EXISTS `beneficio`;
CREATE TABLE IF NOT EXISTS `beneficio` (
  `descripcion` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `puntaje` int(11) NOT NULL,
  `id_beneficio` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_beneficio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `beneficio_canjeado`
--

DROP TABLE IF EXISTS `beneficio_canjeado`;
CREATE TABLE IF NOT EXISTS `beneficio_canjeado` (
  `dni_propietario` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_beneficio` int(11) NOT NULL,
  `fecha` date NOT NULL,
  KEY `id_beneficio` (`id_beneficio`),
  KEY `dni_propietario` (`dni_propietario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `campania`
--

DROP TABLE IF EXISTS `campania`;
CREATE TABLE IF NOT EXISTS `campania` (
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `id_campania` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_campania`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `campania_beneficio`
--

DROP TABLE IF EXISTS `campania_beneficio`;
CREATE TABLE IF NOT EXISTS `campania_beneficio` (
  `id_campania` int(11) NOT NULL,
  `id_beneficio` int(11) NOT NULL,
  KEY `id_campania` (`id_campania`,`id_beneficio`),
  KEY `id_beneficio` (`id_beneficio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
CREATE TABLE IF NOT EXISTS `cuenta` (
  `usuario` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contrasena` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `rol` int(11) DEFAULT NULL,
  `id_cuenta` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_cuenta`),
  KEY `fk_idrol` (`rol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direccion`
--

DROP TABLE IF EXISTS `direccion`;
CREATE TABLE IF NOT EXISTS `direccion` (
  `calle` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `numero` int(11) NOT NULL,
  `barrio` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_retiro`
--

DROP TABLE IF EXISTS `orden_retiro`;
CREATE TABLE IF NOT EXISTS `orden_retiro` (
  `fecha_pedido` date NOT NULL,
  `estado_orden` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `legajo_recolector` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `legajo_recolector` (`legajo_recolector`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido_retiro`
--

DROP TABLE IF EXISTS `pedido_retiro`;
CREATE TABLE IF NOT EXISTS `pedido_retiro` (
  `id_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_del_pedido` date NOT NULL,
  `observacion` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `numero_vivienda` int(11) NOT NULL,
  `vehiculo_pesado` tinyint(1) NOT NULL,
  `id_orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `id_orden` (`id_orden`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propietario`
--

DROP TABLE IF EXISTS `propietario`;
CREATE TABLE IF NOT EXISTS `propietario` (
  `nombre` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dni` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_cuenta` int(11) DEFAULT NULL,
  `puntaje` int(11) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  KEY `id_cuenta` (`id_cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recolector`
--

DROP TABLE IF EXISTS `recolector`;
CREATE TABLE IF NOT EXISTS `recolector` (
  `email` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `turno` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `legajo` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dni` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`legajo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `residuo`
--

DROP TABLE IF EXISTS `residuo`;
CREATE TABLE IF NOT EXISTS `residuo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) DEFAULT NULL,
  `id_tipo_residuo` int(11) NOT NULL,
  `peso` float NOT NULL,
  `id_visita` int(11) DEFAULT NULL,
  `peso_retirado` float DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_residuo` (`id_pedido`,`id_tipo_residuo`),
  KEY `id_tipo_residuo` (`id_tipo_residuo`),
  KEY `id_visita` (`id_visita`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activo` tinyint(1) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_residuo`
--

DROP TABLE IF EXISTS `tipo_residuo`;
CREATE TABLE IF NOT EXISTS `tipo_residuo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_residuo` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `puntos_kilo` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visita`
--

DROP TABLE IF EXISTS `visita`;
CREATE TABLE IF NOT EXISTS `visita` (
  `dia_concurrido` date NOT NULL,
  `observacion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `legajo_recolector` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_orden` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dni_recolector` (`legajo_recolector`),
  KEY `id_orden` (`id_orden`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vivienda`
--

DROP TABLE IF EXISTS `vivienda`;
CREATE TABLE IF NOT EXISTS `vivienda` (
  `numero_vivienda` int(11) NOT NULL AUTO_INCREMENT,
  `dni_propietario` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_direccion` int(11) NOT NULL,
  PRIMARY KEY (`numero_vivienda`),
  KEY `fk_direccion` (`id_direccion`),
  KEY `fk_dni` (`dni_propietario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `beneficio_canjeado`
--
ALTER TABLE `beneficio_canjeado`
  ADD CONSTRAINT `beneficio_canjeado_ibfk_1` FOREIGN KEY (`id_beneficio`) REFERENCES `beneficio` (`id_beneficio`) ON DELETE CASCADE,
  ADD CONSTRAINT `beneficio_canjeado_ibfk_2` FOREIGN KEY (`dni_propietario`) REFERENCES `propietario` (`dni`) ON DELETE CASCADE;

--
-- Filtros para la tabla `campania_beneficio`
--
ALTER TABLE `campania_beneficio`
  ADD CONSTRAINT `campania_beneficio_ibfk_1` FOREIGN KEY (`id_beneficio`) REFERENCES `beneficio` (`id_beneficio`) ON DELETE CASCADE,
  ADD CONSTRAINT `campania_beneficio_ibfk_2` FOREIGN KEY (`id_campania`) REFERENCES `campania` (`id_campania`) ON DELETE CASCADE;

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `fk_idrol` FOREIGN KEY (`rol`) REFERENCES `roles` (`codigo`);

--
-- Filtros para la tabla `orden_retiro`
--
ALTER TABLE `orden_retiro`
  ADD CONSTRAINT `orden_retiro_ibfk_1` FOREIGN KEY (`legajo_recolector`) REFERENCES `recolector` (`legajo`) ON DELETE CASCADE;

--
-- Filtros para la tabla `pedido_retiro`
--
ALTER TABLE `pedido_retiro`
  ADD CONSTRAINT `pedido_retiro_ibfk_1` FOREIGN KEY (`id_orden`) REFERENCES `orden_retiro` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `propietario`
--
ALTER TABLE `propietario`
  ADD CONSTRAINT `fk_id_cuenta` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id_cuenta`);

--
-- Filtros para la tabla `residuo`
--
ALTER TABLE `residuo`
  ADD CONSTRAINT `Residuo_ibfk_1` FOREIGN KEY (`id_tipo_residuo`) REFERENCES `tipo_residuo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `Residuo_ibfk_2` FOREIGN KEY (`id_pedido`) REFERENCES `pedido_retiro` (`id_pedido`) ON DELETE CASCADE,
  ADD CONSTRAINT `Residuo_ibfk_3` FOREIGN KEY (`id_visita`) REFERENCES `visita` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `vivienda`
--
ALTER TABLE `vivienda`
  ADD CONSTRAINT `fk_direccion` FOREIGN KEY (`id_direccion`) REFERENCES `direccion` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_dni` FOREIGN KEY (`dni_propietario`) REFERENCES `propietario` (`dni`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
