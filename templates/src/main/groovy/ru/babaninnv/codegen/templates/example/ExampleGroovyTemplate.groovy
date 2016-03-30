package ru.babaninnv.codegen.templates.example

import ru.babaninnv.codegen.templates.Model
import ru.babaninnv.codegen.templates.Template

/**
 * Created by BabaninN on 29.03.2016.
 */
class ExampleGroovyTemplate implements Template {

    private Writer w
    private Model model

    @Override
    void setup(Writer writer, Model model) {
        this.model = model
        this.w = writer
    }

    @Override
    void render() {
        w.write("""
class ExmapleTemplate {

    def foo() {
        System.out.println("Hello Template");
    }

}
        """)
    }
}
