package ru.otus.spring.enums;

public enum NotificationCode {
        /**
         * Лимиты проверены успешно. Ошибок нет
         */
        LIMIT_CHECK_SUCCESS(0, "exceptions.limit.LimitCheckSuccess"),
        /**
         * Превышение по лимиту или ошибка выполнения метода
         */
        LIMIT_CHECK_EXCESS(1, "exceptions.limit.LimitCheckExcess"),

        /*Лимит не найден по идентификатору*/
        LIMIT_NOT_FOUND(2, "exceptions.limit.LimitNotFound"),

        LIMIT_NAME_IS_MANDATORY(3, "exceptions.limit.limitNameIsMandatory"),
        LIMIT_BREAKCONDITIONVALUE_IS_MANDATORY(4, "exceptions.limit.BreakConditionValueIsMandatory"),
        LIMIT_CONDITIONLIST_IS_MANDATORY(5, "exceptions.limit.ConditionListIsMandatory"),
        LIMIT_NEED_TO_SET_CURRENCYPARAMS(6, "exceptions.limit.NeedToSetCurrencyParams"),
        LIMIT_WITH_NAME_ALREADYEXISTS(7, "exceptions.limit.LimitWithNameAlreadyExists"),
        LIMIT_CURRENCY_NOTFOUND_BYPARAM(8, "exceptions.limit.CurrencyNotFoundByParams");

        private final int code;
        private final String message;

        /**
         * Конструктор определяет тип ошибки с указанием и сообщения
         * её числового кода
         * @param code
         */
        NotificationCode(int code, String message) {
                this.code = code;
                this.message = message;
        }

        /**
         * Возвращает числовой код ошибки
         * @return
         */
        public long getCode() {
                return (long) code;
        }

        /**
         * Возвращает числовой код ошибки как строку
         */
        @Override
        public String toString() {
                return Integer.toString(code);
        }

        /**
         *
         * @return
         */
        public String getMessage() {
                return message;
        }

        public static NotificationCode valueOf(int value){
                for(NotificationCode code : NotificationCode.values()){
                        if(code.code == value)
                                return code;
                }
                return null;
        }
        }
