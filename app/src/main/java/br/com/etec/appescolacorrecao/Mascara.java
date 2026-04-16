package br.com.etec.appescolacorrecao;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mascara {

    // Melhore o unmask para remover TUDO que não for número
    public static String unmask(String s) {
        return s.replaceAll("[^0-9]", "");
    }

    public static TextWatcher maskcpf(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = unmask(s.toString());
                String mask = "###.###.###-##";
                String cpf = "";

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#') {
                        // Só adiciona o caractere especial se ainda houver números para processar
                        if (str.length() > i) {
                            cpf += m;
                        }
                        continue;
                    }

                    try {
                        cpf += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;
                editText.setText(cpf);
                if (cpf.length() <= editText.getText().length()) {
                    editText.setSelection(cpf.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }
}
