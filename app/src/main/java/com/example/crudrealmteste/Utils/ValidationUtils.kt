package com.example.crudrealmteste.Utils

import android.util.Patterns
import android.widget.EditText

class ValidationUtils {
    companion object {
        fun isCNPJValid(cnpj: String): Boolean {
            // Remove caracteres não numéricos do CNPJ
            val cleanedCNPJ = cnpj.replace("[^0-9]".toRegex(), "")

            // Verifica se o CNPJ tem 14 dígitos
            if (cleanedCNPJ.length != 14) {
                return false
            }

            // Verifica se todos os dígitos são iguais, o que não é permitido
            if ((0..9).all { cleanedCNPJ == it.toString().repeat(14) }) {
                return false
            }

            // Calcula o primeiro dígito verificador
            val dv1 = cleanedCNPJ.substring(12, 13).toInt()
            val sum1 = (0..11).sumOf { cleanedCNPJ[it].toString().toInt() * (5 - (it % 4)) }
            val remainder1 = sum1 % 11
            val calculatedDv1 = if (remainder1 < 2) 0 else 11 - remainder1

            // Calcula o segundo dígito verificador
            val dv2 = cleanedCNPJ.substring(13, 14).toInt()
            val sum2 = (0..11).sumOf {
                cleanedCNPJ[it].toString().toInt() * (6 - (it % 4))
            } + calculatedDv1 * 2
            val remainder2 = sum2 % 11
            val calculatedDv2 = if (remainder2 < 2) 0 else 11 - remainder2

            // Verifica se os dígitos verificadores calculados batem com os dígitos do CNPJ
            return calculatedDv1 == dv1 && calculatedDv2 == dv2
        }

        fun isCPFValid(cpf: String): Boolean {
            // Remove caracteres não numéricos do CPF
            val cleanedCPF = cpf.replace("[^0-9]".toRegex(), "")

            // Verifica se o CPF tem 11 dígitos
            if (cleanedCPF.length != 11) {
                return false
            }

            // Verifica se todos os dígitos são iguais, o que não é permitido
            if ((0..9).all { cleanedCPF == it.toString().repeat(11) }) {
                return false
            }

            // Calcula o primeiro dígito verificador
            val dv1 = cleanedCPF.substring(9, 10).toInt()
            val sum1 = (0..8).sumOf { cleanedCPF[it].toString().toInt() * (10 - it) }
            val remainder1 = sum1 % 11
            val calculatedDv1 = if (remainder1 < 2) 0 else 11 - remainder1

            // Calcula o segundo dígito verificador
            val dv2 = cleanedCPF.substring(10, 11).toInt()
            val sum2 =
                (0..8).sumOf { cleanedCPF[it].toString().toInt() * (11 - it) } + calculatedDv1 * 2
            val remainder2 = sum2 % 11
            val calculatedDv2 = if (remainder2 < 2) 0 else 11 - remainder2

            // Verifica se os dígitos verificadores calculados batem com os dígitos do CPF
            return calculatedDv1 == dv1 && calculatedDv2 == dv2
        }

        fun isFieldValid(field: EditText, fieldName: String): Boolean {
            val fieldValue = field.text.toString().trim()
            if (fieldValue.isEmpty()) {
                field.error = "$fieldName é obrigatório"
                return false
            }
            return true
        }

        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}